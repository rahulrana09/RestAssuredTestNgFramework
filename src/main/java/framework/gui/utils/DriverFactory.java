package framework.gui.utils;

import framework.ConfigLoader;
import framework.globalConstants.FilePath;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;

public class DriverFactory {

    private static DriverFactory instance = null;
    private WebDriver driver;

    private DriverFactory() {
        try {
            if (ConfigLoader.getInstance().getBrowserType().equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();

                // preferences
                HashMap<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("download.default_directory", FilePath.dirFileDownloads);
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("profile.default_content_setting_values.notifications", 2);

                // options
                ChromeOptions options = new ChromeOptions();
                options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
                options.setExperimentalOption("prefs", chromePrefs);
                options.setCapability(ChromeOptions.CAPABILITY, options);

                driver = new ChromeDriver(options);
                driver.manage().timeouts().implicitlyWait(ConfigLoader.getInstance().getImplicitWait());
                driver.manage().window().maximize();
            } else if (ConfigLoader.getInstance().getBrowserType().equalsIgnoreCase("ie")) {
                // do ie driver init, same for mozilla
            }
        } catch (Exception e) {
            throw new RuntimeException("ABORT! Failed to get Selenium Webdriver !");
        }
    }


    public static DriverFactory getInstance() {
        if (instance == null) {
            synchronized (DriverFactory.class) {
                if (instance == null) {
                    instance = new DriverFactory();
                }
            }
        }
        return instance;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            instance = null;
        }
    }

    public void closeDriver() {
        if (driver == null)
            return;

        driver.close();
        driver = null;
    }


}
