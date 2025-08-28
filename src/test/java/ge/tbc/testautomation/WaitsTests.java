package ge.tbc.testautomation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.steps.theinternet.DynamicControlsSteps;
import org.testng.annotations.*;

import java.util.Arrays;

public class WaitsTests {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    DynamicControlsSteps dynamicControlsSteps;

    @BeforeClass
    public void setUp(){
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions"));
        launchOptions.setHeadless(false);
//        launchOptions.setSlowMo(1000);
//        launchOptions.setTimeout(50000);
        browser = playwright.chromium().launch(launchOptions);
    }

    @BeforeMethod
    public void setContext(){
        browserContext = browser.newContext();
        page = browserContext.newPage();
        dynamicControlsSteps = new DynamicControlsSteps(page);
        page.navigate("https://the-internet.herokuapp.com/dynamic_controls");
    }

    @Test
    public void testAutoWaits() {
        dynamicControlsSteps
                .clickEnableButton()
                .clickDisableButton();
    }

    @Test
    public void testExplicitWait() {
        dynamicControlsSteps
                .clickEnableButton();
        page.waitForSelector("img[src='/img/ajax-loader.gif']", new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.HIDDEN).setTimeout(10000));
        dynamicControlsSteps.validateMessage(Constants.EXPECTED_ENABLED_TEXT);
    }


    @AfterMethod
    public void closeContext(){
        browserContext.close();
    }

    @AfterClass
    public void tearDown(){
        browser.close();
        playwright.close();
    }
}
