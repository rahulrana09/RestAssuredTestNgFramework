package framework.pageObjects;

import framework.ConfigLoader;
import framework.gui.utils.DriverFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FunctionalInterface
interface Operation {
    void execute() throws Exception;
}

public class PageUtil {
    private static final Logger logger = LoggerFactory.getLogger(PageUtil.class);
    public static final int MAX_RETRIES = 3;
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static WebDriverWait smallWait;
    protected static WebDriverWait longWait;
    protected static long stepWait;
    protected static Actions actions;

    public PageUtil() {
        driver = DriverFactory.getInstance().getDriver();
        PageFactory.initElements(driver, this);
        if (smallWait == null) {
            smallWait = new WebDriverWait(driver, ConfigLoader.getInstance().getExplicitSmallWait());
            wait = new WebDriverWait(driver, ConfigLoader.getInstance().getExplicitWait());
            longWait = new WebDriverWait(driver, ConfigLoader.getInstance().getExplicitLongWait());
            stepWait = ConfigLoader.getInstance().getStepWaitTime();
            actions = new Actions(driver);
        }
    }

    /**
     * Get Text
     *
     * @param element [WebElement]
     * @return
     */
    @Step
    protected String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return wait.until(ExpectedConditions.elementToBeClickable(element)).getText();
    }

    @Step
    protected boolean isElementExist(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Set Text on a WebElement
     *
     * @param elem        [WebElement] target element UI
     * @param text        [String] Value to be set
     * @param elementName [String] UI Element reference name
     *                    e.g. setText(txtUserName, "Dr. Who", "UserName");
     */
    @Step
    protected static void setText(WebElement elem, String text, String elementName) {
        try {
            executeWithRetries(() -> {
                wait.until(ExpectedConditions.elementToBeClickable(elem)).clear();
                elem.sendKeys(text);
                logInfo("set " + elementName + "'s text as '" + text + "'");
            }, MAX_RETRIES, stepWait);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set text: " + e.getMessage());
        }
    }

    @Step
    protected static void clickOnElement(WebElement elem, String elementName) {
        try {
            executeWithRetries(() -> {
                wait.until(ExpectedConditions.elementToBeClickable(elem));
                elem.click();
                logInfo("Click on " + elementName);
            }, MAX_RETRIES, stepWait);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set text: " + e.getMessage());
        }
    }

    /**
     * log info
     *
     * @param info
     */
    protected static void logInfo(String info) {
        logger.info(info); // log for Extent Report
        Allure.step(info);
    }


    /**
     * Executes an operation and retries up to maxRetries times if it fails,
     * with a delay between retries.
     *
     * @param operation        The operation to execute.
     * @param maxRetries       The maximum number of retries.
     * @param retryDelayMillis The delay between retries in milliseconds.
     * @throws Exception If the operation fails all attempts.
     */
    public static void executeWithRetries(Operation operation, int maxRetries, long retryDelayMillis) throws Exception {
        int attempt = 0;
        while (true) {
            try {
                operation.execute();  // Attempt to execute the operation
                Thread.sleep(retryDelayMillis); // buffer wait after success step
                return;  // Success, exit the method
            } catch (Exception ex) {
                attempt++;
                if (attempt > maxRetries) {
                    throw ex;  // All attempts failed, rethrow the last exception
                }
                try {
                    Thread.sleep(retryDelayMillis);  // Delay before next attempt
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();  // Restore the interrupted status
                    throw new Exception("Retry operation was interrupted", ie);
                }
            }
        }
    }


}