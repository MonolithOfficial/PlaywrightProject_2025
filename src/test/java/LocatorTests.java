import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.regex.Pattern;

public class LocatorTests {
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
        launchOptions.setSlowMo(1000);
        launchOptions.setTimeout(50000);
        browser = playwright.chromium().launch(launchOptions);
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }

    @BeforeMethod
    public void openPage(){
        page.navigate("https://practicesoftwaretesting.com/");
    }

    @Test
    public void testLocators() {
        Locator searchField = page.getByLabel("Search");
        searchField.fill("Pliers");

        Locator combinationPliersCard = page.getByText("Combination Pliers");
//        Locator combinationPliersCard = page
//                .locator("//h5[text()='Combination Pliers']");
        combinationPliersCard.click();

        Locator allCards = page.locator("//h5[@class='card-title']");
//        PlaywrightAssertions.assertThat(allCards).

        for (Locator card : allCards.all()){
            card.hover();
            System.out.println(card.textContent());
        }

        Locator filteredCards = allCards.filter(
                new Locator.FilterOptions()
                        .setHas(page.getByText(Pattern.compile("Pliers")))
        );

        System.out.println("================== filtering");
        for (Locator card : filteredCards.all()){
            card.hover();
            System.out.println(card.textContent());
        }
        PlaywrightAssertions.assertThat(filteredCards).hasCount(3);

        Locator productName = page.getByTestId("product-name");
        PlaywrightAssertions.assertThat(productName).hasText("Combination Pliers");
    }

    @Test
    public void testGetByRole() {
        // TRY FIND HAMMER CHECKBOX BY ITS ROLE
        Locator hammerCheckBox = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Hammer"));
        PlaywrightAssertions.assertThat(hammerCheckBox).isVisible();

        // TRY FIND SEARCH BUTTON BY ITS ROLE
        Locator searchButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search"));
        PlaywrightAssertions.assertThat(searchButton).isVisible();
    }

    @Test
    public void testLocatorMethod() {
//        Locator hammerCheckBox = page.locator("//label[contains(text(), 'Hammer')]")
//                .locator("input");
//        PlaywrightAssertions.assertThat(hammerCheckBox).isVisible();
//
//        Locator searchButton = page.locator("button[type='submit']"); // CSS SELECTOR;
//        PlaywrightAssertions.assertThat(searchButton).isVisible();

        Locator wrenchCheckBox = page
                .locator("fieldset")
                .locator("div.checkbox")
                .getByLabel("Wrench");
        PlaywrightAssertions.assertThat(wrenchCheckBox).isVisible();
    }

    @Test
    public void testLists() {
        // Get all checkboxes in div.checkboxes which have labels contain 'er' sequence
        Locator allCheckBoxDivs = page.locator("//div[@class='checkbox']//ul//div")
                .filter(new Locator
                        .FilterOptions()
                        .setHas(page
                                .getByText(Pattern.compile("er"))));
        System.out.println(allCheckBoxDivs.count());
        for (Locator checkBox :
                allCheckBoxDivs.all()) {
            // click each checkbox
            checkBox.scrollIntoViewIfNeeded();
            checkBox.locator("input").check(); // note usage of check() method, not click()
        }
    }

    @Test
    public void cardPricesShouldContainDollarSignsTest() {
        Locator allCards = page.locator(".card");
        for (Locator card :
                allCards.all()) {
            PlaywrightAssertions.assertThat(card.locator(".card-footer")).containsText("$");
        }
    }

    @AfterClass
    public void tearDown(){
        browserContext.close();
        browser.close();
        playwright.close();
    }
}
