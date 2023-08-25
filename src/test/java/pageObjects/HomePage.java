package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HomePage extends BasePage {

    private static final By BY_STATED_EVENT = By.xpath("//div[@class='kp op']//a");
    private static final By BY_BUTTON_COOKIES = By.xpath("//div[@id='simpleCookieBar']//button[@id='simpleCookieBarCloseButton']");
    private static final By BY_DATE_ELEMENT = By.xpath("//div[@class='uh']/span[@id='SEV__status'][text()]");
    private static final By BY_TIME_ELEMENT = By.xpath("//div[@class='uh']/span[@id='score-or-time'][text()]");
    private static final By BY_SITE_MENU = By.xpath("//div[@class='Di']/span[@id='burger-menu-open']");
    private static final By BY_BURGER_MENU = By.xpath("//a[contains(@id,'burger-menu')]//span[text()]");
    private static final By BY_TIME_ZONE = By.xpath("//div[@id='TZ_SELECT']");
    private static final By BY_CHOOSE_TIME_ZONE = By.xpath("//div[@class='gj selectItems']/div");
    private static final By BY_BUTTON_APPLY = By.xpath("//div[contains(@id,'apply-wrapper')]/button");
    private LocalTime timeOfEvent;
    private LocalDate dateOfEvent;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickButtonCookie() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement cookiesButton = wait.until(ExpectedConditions.elementToBeClickable(BY_BUTTON_COOKIES));
        cookiesButton.click();
    }

    public void selectEvent() {
        List<WebElement> menuElements = getDriver().findElements(waitForDisplayed(BY_STATED_EVENT));

        for (WebElement element : menuElements) {
            try {
                if (element.isEnabled()) {
                    element.click();
                    return;
                }
            } catch (StaleElementReferenceException e) {
            }
        }

        int totalScrolls = 5;
        int scrollToPixels = 500;

        for (int scrollCount = 0; scrollCount < totalScrolls; scrollCount++) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
            jsExecutor.executeScript("window.scrollBy(0, " + scrollToPixels + ");");
            for (WebElement element : menuElements) {
                try {
                    if (element.isEnabled()) {
                        scrollToElement(element);
                        element.click();
                        return;
                    }
                } catch (StaleElementReferenceException e) {
                }
            }
        }
    }

    public void timeAndData() {
        String dateText = getDriver().findElement(waitForDisplayed(BY_DATE_ELEMENT)).getText();
        String timeText = getDriver().findElement(waitForDisplayed(BY_TIME_ELEMENT)).getText();
        parseDateAndTimeText(dateText, timeText);
    }

    private void parseDateAndTimeText(String dateText, String timeText) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        if (dateText.equalsIgnoreCase("today")) {
            dateOfEvent = LocalDate.now();
        } else {
            dateOfEvent = LocalDate.parse(dateText, dateFormatter);
        }
        timeOfEvent = LocalTime.parse(timeText, timeFormatter);
    }

    public LocalDate getDateOfEvent() {
        return dateOfEvent;
    }

    public LocalTime getTimeOfEvent() {
        return timeOfEvent;
    }

    public void clickButtonSiteMenu() {
        getDriver().findElement(waitForDisplayed(BY_SITE_MENU)).click();
    }

    public void clickButtonSetting(String menu) {
        List<WebElement> menuElements = getDriver().findElements(waitForDisplayed(BY_BURGER_MENU));
        menuElements.stream()
                .filter(el -> menu.equals(el.getText()))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public void clickTimeZone() {
        getDriver().findElement(waitForDisplayed(BY_TIME_ZONE)).click();
    }

    public void chooseRandomTimeZone(int index) {
        List<WebElement> timeZoneElements = getDriver().findElements(waitForDisplayed(BY_CHOOSE_TIME_ZONE));
//        if (timeZoneElements.size() > 1) {
//            timeZoneElements.stream()
//                    .skip(1)
//                    .findFirst()
//                    .ifPresent(WebElement::click);
        if (index >= 0 && index < timeZoneElements.size()) {
            timeZoneElements.stream()
                    .skip(index)
                    .findFirst()
                    .ifPresent(WebElement::click);
        }
    }

    public void clickButtonApply() {
        getDriver().findElement(waitForDisplayed(BY_BUTTON_APPLY)).click();
    }
}