package ge.tbc.testautomation;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class AlertsFramesTabsTest {
    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false).setSlowMo(1000);

        browser = playwright.chromium().launch(launchOptions);
        context = browser.newContext();
        page = context.newPage();
        page.setDefaultTimeout(50000);
    }

    @Test
    public void testAlerts() {
        page.navigate("https://demoqa.com/alerts");
        Locator alertBtn = page.locator("button#alertButton");
        alertBtn.click();

        page.onDialog(Dialog::accept);
//        page.onDialog(Dialog::dismiss);
//        page.onDialog(Dialog::accept);
    }

    @Test
    public void testFrames() {
        page.navigate("https://the-internet.herokuapp.com/nested_frames");
        Locator middleFrameText = page
                .frameLocator("[name='frame-top']")
                .frameLocator("[name='frame-middle']")
                .locator("div#content");
        System.out.println(middleFrameText);
    }

    @Test
    public void testTabs() {
        page.navigate("https://stackoverflow.com");
        Page page2 = context.newPage();
        page2.navigate("https://google.com");

        Page page3 = context.newPage();
        page3.navigate("https://cnn.com");

        page3.bringToFront();
        page2.bringToFront();

//        page3.close();

        List<Page> allPages = context.pages();
        allPages.forEach(page -> System.out.println(page.title()));
    }

    @AfterClass
    public void tearDown(){
        context.close();
        browser.close();
        playwright.close();
    }
}
