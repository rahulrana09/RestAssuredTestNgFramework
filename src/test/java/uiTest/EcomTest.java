package uiTest;

import framework.common.Assertion;
import framework.common.DataFactory;
import framework.gui.UiTestInit;
import framework.gui.features.Login;
import framework.gui.utils.DriverFactory;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EcomTest extends UiTestInit {


    @Test(priority = 1)
    @Epic("Web interface")
    @Feature("Essential features")
    @Story("Authentication")
    @Description("This test attempts to log into the website using a valid login and a password. " +
            "Fails if any error happens or user is not on Home Page.")
    @Owner("John Doe")
    public void testOne() throws Exception {
        try{
            Login.loginAsUser("standard_user");
            Login.logOut();
        }catch (Exception e) {
            markTestAsFailure(e);
        } finally {
            Assertion.finalizeSoftAsserts();
        }
    }


}
