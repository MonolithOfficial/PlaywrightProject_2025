package ge.tbc.testautomation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.steps.theinternet.DynamicControlsSteps;
import io.qameta.allure.*;
import org.testng.annotations.*;

import java.util.Arrays;

//@SomeAnnotation("SQDTBC-T1616") // in case of scenarios
@Test(description = "Tests for Waiting functionality")
//@Story("")
//@Feature("")
//@Epic("")
public class WaitsTests {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    DynamicControlsSteps dynamicControlsSteps;

    @BeforeClass
    @Parameters({"browserType"})
    public void setUp(String browserType){
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions"));
        launchOptions.setHeadless(false);
//        launchOptions.setSlowMo(1000);
//        launchOptions.setTimeout(50000);
        if (browserType.equals("chromium")){
            browser = playwright.chromium().launch(launchOptions);
        }
        else {
            browser = playwright.webkit().launch(launchOptions);
        }
    }

    @BeforeMethod
    public void setContext(){
        browserContext = browser.newContext();
        page = browserContext.newPage();
        dynamicControlsSteps = new DynamicControlsSteps(page);
        page.navigate("https://the-internet.herokuapp.com/dynamic_controls");
    }

    @Test(priority = 1, description = "SQDTBC-T2727 Testing automatic waits from Playwright")
    @Description("Some more detailed text")
    @Severity(SeverityLevel.BLOCKER)
    @Link("https://fake-jira.com/testcase/SQDTBC-T2828")
    @Flaky
    public void testAutoWaits() {
        dynamicControlsSteps
                .clickEnableButton()
                .clickDisableButton();
    }

    @Test(priority = 2, description = "SQDTBC-T2828 Testing explicit waits from Playwright")
    @Description("Some more detailed text")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://fake-jira.com/testcase/SQDTBC-T2828")
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
