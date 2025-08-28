package ge.tbc.testautomation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.steps.theinternet.DynamicControlsSteps;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class MoreWaitsTests {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;

    @BeforeClass
    public void setUp(){
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions"));
        launchOptions.setHeadless(false);
//        launchOptions.setSlowMo(1000);
//        launchOptions.setTimeout(50000);
        browser = playwright.chromium().launch(launchOptions);
        browserContext = browser.newContext();
        page = browserContext.newPage();
        page.navigate("https://practicesoftwaretesting.com/");
    }

    @Test
    public void testWaitsOnCollectionOfElements() {
        Locator productNames = page.locator("[data-test='product-name']");
//        page.waitForSelector("[data-test='product-name']"); alternative wait
//        Locator productNames = page.getByTestId("product-name");
        PlaywrightAssertions.assertThat(productNames).hasCount(9);
        List<String> productNamesTexts = productNames.allInnerTexts();
        System.out.println(productNamesTexts.size());
        Assert.assertTrue(productNamesTexts.containsAll(List.of("Combination Pliers", "Bolt Cutters", "Thor Hammer")));
    }

    @AfterClass
    public void tearDown(){
        browserContext.close();
        browser.close();
        playwright.close();
    }
}
