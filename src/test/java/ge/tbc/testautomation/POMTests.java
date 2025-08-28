package ge.tbc.testautomation;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import ge.tbc.testautomation.steps.practicesoftwaretesting.ProductsSteps;
import org.testng.annotations.*;

public class POMTests {
    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;
    ProductsSteps productsSteps;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @BeforeMethod
    public void isolateContext(){
        context = browser.newContext();
        page = context.newPage();
        productsSteps = new ProductsSteps(page);
        page.navigate("https://practicesoftwaretesting.com/");
    }

    @Test
    public void validateSearchResultPOM() {
        productsSteps
                .searchForItem("Pliers")
                .validateNumberOfCards(9);
    }

    @Test
    public void validateSearchResultPOM2() {
        page.navigate("https://practicesoftwaretesting.com/");
        productsSteps
                .searchForItem("Hammer")
                .validateNumberOfCards(9);
    }

    @AfterMethod
    public void contextTearDown() {
        context.close();
    }

    @AfterClass
    public void browserTearDown() {
        browser.close();
        playwright.close();
    }


}
