package framework.common;

import framework.gui.utils.DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenShot {

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] captureScreen() {
        // Take screenshot and return as byte array
        return ((TakesScreenshot) DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
    }

}
