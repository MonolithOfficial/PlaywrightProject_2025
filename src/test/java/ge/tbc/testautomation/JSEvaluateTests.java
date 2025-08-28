package ge.tbc.testautomation;

import com.microsoft.playwright.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class JSEvaluateTests {
    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false).setSlowMo(2000);

        browser = playwright.chromium().launch(launchOptions);
        context = browser.newContext();
        page = context.newPage();
        page.setDefaultTimeout(50000);
        page.navigate("https://swoop.ge");
    }

    @Test
    public void testJSWaitForFunction() {
//        page.waitForFunction("() => document.readyState === 'complete'", new Page.WaitForFunctionOptions().setTimeout(50000));
//        String readyState = (String) page.evaluate("document.readyState");
//
//        System.out.println(readyState);

        Locator exitButton = page.locator("//button[@type='button']//img[@src='/icons/closeEmpty.svg']");
        Locator discoverButton = page.locator("//p[text()='აღმოაჩინე']//parent::button");
//        exitButton.evaluate("element => element.click()");
//
//        page.evaluate("element => element.click()", exitButton.elementHandle());
//
        Map<String, ElementHandle> elementHandles = new HashMap<>();
        elementHandles.put("exitButton", exitButton.elementHandle());
        elementHandles.put("discoverButton", discoverButton.elementHandle());

        page.evaluate("({ exitButton, discoverButton }) => {discoverButton.click(); exitButton.click();}", elementHandles);
    }

    public void contextTearDown() {
        context.close();
        browser.close();
        playwright.close();
    }
}
