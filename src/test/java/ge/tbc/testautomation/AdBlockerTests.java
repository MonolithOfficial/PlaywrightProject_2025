package ge.tbc.testautomation;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.Arrays;

import static ge.tbc.testautomation.data.Constants.EXTENSION_PATH;
import static ge.tbc.testautomation.data.Constants.TMP_PATH;

public class AdBlockerTests {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;

    @BeforeClass
    public void setUp(){
        playwright = Playwright.create();
        browserContext = playwright.chromium().launchPersistentContext(
                Paths.get(TMP_PATH),
                new BrowserType.LaunchPersistentContextOptions()
                        .setHeadless(false)
                        .setSlowMo(10000)
                        .setArgs(Arrays.asList(
                                String.format("--load-extensions=%s", EXTENSION_PATH),
                                String.format("--disable-extensions-except=%s", EXTENSION_PATH)
                        ))
        );
        page = browserContext.newPage();
    }

    @Test
    public void testAdBlocker() {
        page.navigate("https://magento.softwaretestingboard.com/");
        Locator writeForUsLink = page.locator("//a[text()='Write for us']");
        writeForUsLink.scrollIntoViewIfNeeded();
        writeForUsLink.click();
    }

    @AfterClass
    public void tearDown(){
        browserContext.close();
        playwright.close();
    }
}
