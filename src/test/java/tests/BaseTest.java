package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import pageObjects.BasePage;


public class BaseTest {

    private WebDriver driver;

    public void setup(String browser, String testUrl) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-extensions");
        options.addArguments("--verbose");
        driver = new ChromeDriver(options);
        BasePage basePage = new BasePage(driver);
        basePage.openMainPage(testUrl);
    }

    public WebDriver getDriver() {
        return driver;
    }

    protected void closeDriver() {
        if ((driver != null)) {
            driver.close();
            driver.quit();
        }
    }

    @AfterClass
    public void shutDown() {
        closeDriver();
    }
}
