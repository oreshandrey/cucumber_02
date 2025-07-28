package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.util.HashMap;
import java.util.Map;

public class Driver {

    static private ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    private Driver(){}

    public static synchronized WebDriver getDriver(){
        ChromeOptions options;

        if(drivers.get() == null) {
            String browser = System.getProperty("browser");
            if(browser == null){
                browser = PropertyReader.readProperty("browser");
            }

            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    options = new ChromeOptions();
                    Map<String, Object> chromePrefs = new HashMap<>();
                    chromePrefs.put("profile.password_manager_leak_detection", false);
                    chromePrefs.put("profile.password_manager_enabled", false);
                    chromePrefs.put("credentials_enable_service", false);
                    chromePrefs.put("autofill.profile_enabled", false);
                    options.setExperimentalOption("prefs", chromePrefs);
                    drivers.set(new ChromeDriver(options));
                    break;
                case "chromeheadless":
                    WebDriverManager.chromedriver().setup();
                    options = new ChromeOptions();
                    options.addArguments("--headless");
                    drivers.set(new ChromeDriver(options));
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    drivers.set(new EdgeDriver());
                    break;
                case "edgeheadless":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions eoptions = new EdgeOptions();
                    eoptions.addArguments("--headless");
                    drivers.set(new EdgeDriver(eoptions));
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    drivers.set(new FirefoxDriver());
                    break;
                case "firefoxheadless":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions foptions = new FirefoxOptions();
                    foptions.addArguments("--headless");
                    drivers.set(new FirefoxDriver(foptions));
                case "safari":
                    WebDriverManager.safaridriver().setup();
                    drivers.set(new SafariDriver());
                    break;
                default:
                    throw new RuntimeException("Invalid browser");
            }
        }
        return drivers.get();
    }


    public static synchronized void closeDriver(){
        if(drivers.get() != null){
            drivers.get().quit();
            drivers.remove();
        }
    }

}
