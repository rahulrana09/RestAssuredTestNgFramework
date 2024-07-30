package framework.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends PageUtil {

    @FindBy(id = "user-name")
    WebElement txtUserName;

    @FindBy(id = "password")
    WebElement txtPassword;

    @FindBy(id = "login-button")
    WebElement btnSubmit;

    @FindBy(id = "react-burger-menu-btn")
    WebElement leftNavButton;

    @FindBy(id = "logout_sidebar_link")
    WebElement linkLogout;

    public boolean isMainPageAvailable() {
        return isElementExist(leftNavButton);
    }

    public boolean isLoginPageAvailable() {
        return isElementExist(txtUserName);
    }

    public LoginPage clickExpandLeftNav() {
        clickOnElement(leftNavButton, "leftNavButton");
        return this;
    }

    public void clickOnLogout() {
        clickOnElement(linkLogout, "linkLogout");
    }

    public LoginPage setPassword(String password) {
        setText(txtPassword, password, "Password");
        return this;
    }

    public LoginPage setUserName(String userName) {
        setText(txtUserName, userName, "UserName");
        return this;
    }

    public void clickOnSubmit() {
        clickOnElement(btnSubmit, "Submit Login Form");
    }

    public LoginPage() {
        super();
    }


}
