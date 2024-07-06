package Singleton;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LazyInit {
    private static WebDriver driver;
    private static LazyInit lInit;

    private LazyInit() {

    }

    public static LazyInit getInstance() {
        if (lInit == null) {
            lInit = new LazyInit();
        }
        return lInit;
    }

    public static WebDriver getDriver(String browser) {
        if (driver == null) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Browser");
            }
        }
        return driver;
    }


}
