package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.HomePage;

public class LiveScoreTest extends BaseTest {

    protected HomePage homePage;

    @Parameters({"browser", "testUrl"})
    @BeforeClass
    public void setUpOne(String browser, String testUrl) {
        setup(browser, testUrl);
    }

    @Test
    public void testLiveScore() {
        homePage = new HomePage(getDriver());
        homePage.clickButtonCookie();
        homePage.selectEvent();

        homePage.timeAndData();
        System.out.println("Before time zone change:");
        System.out.println(homePage.getTimeOfEvent());
        System.out.println(homePage.getDateOfEvent());

        homePage.clickButtonSiteMenu();
        homePage.clickButtonSetting("Settings");
        homePage.clickTimeZone();
        homePage.chooseRandomTimeZone(5);
        homePage.clickButtonApply();

        homePage.timeAndData();
        System.out.println("After time zone change:");
        System.out.println(homePage.getTimeOfEvent());
        System.out.println(homePage.getDateOfEvent());
    }
}

