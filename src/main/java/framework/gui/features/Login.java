package framework.gui.features;

import framework.ConfigLoader;
import framework.common.Assertion;
import framework.common.DataFactory;
import framework.entity.User;
import framework.gui.utils.DriverFactory;
import framework.pageObjects.LoginPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Login {
    private static String lastUser;
    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    /**
     * Login with specific user, if user is already logged in > return
     *
     * @param loginId
     * @throws IOException
     */
    @Step
    public static void loginAsUser(String loginId) throws Exception {
        Allure.label("method", "Login as: " + loginId);
        User autUser = DataFactory.getAutUser(loginId);

        // check if user is already logged in with current View
        if (lastUser != null && lastUser.equalsIgnoreCase(autUser.loginId)) {
            logger.info("Already Logged in as User: " + loginId);
            return;
        }

//      logged in as different User
        if (lastUser != null) {
            logOut();
        }

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            driver.get(ConfigLoader.getInstance().getURL());

            LoginPage loginPage = new LoginPage();
            loginPage
                    .setUserName(autUser.loginId)
                    .setPassword(autUser.password)
                    .clickOnSubmit();

            // Verify that the User is logged in and Dashboard is the default page
            if (Assertion.verifyEqual(loginPage.isMainPageAvailable(),
                    true,
                    "Check if Main Page is Available")) {
                lastUser = loginId;
            }
        } catch (Exception e) {
            throw new Exception("Failed to login as user " + loginId, e);
        }
    }

    /**
     * Log out from application, close any additional Tabs
     *
     * @throws Exception
     */
    @Step
    public static void logOut() throws Exception {
        Allure.label("method", "Initiate Logout");
        try {
            if (lastUser == null) {
                Allure.step("No Last User Logged In!");
                return;
            }
            LoginPage loginPage = new LoginPage();
            loginPage
                    .clickExpandLeftNav()
                    .clickOnLogout();

            // verify successful logout
            Assertion.verifyEqual(loginPage.isLoginPageAvailable(),
                    true,
                    "Verify that the user is directed to the login page");

        } catch (Exception e) {
            throw new Exception("Failed to login as user " + lastUser, e);
        } finally {
            lastUser = null;
            DriverFactory.getInstance().closeDriver();
        }
    }


}
