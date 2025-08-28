package ge.tbc.testautomation.pages.practicesoftwaretesting;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProductsPage {
    public Locator searchInput;
    public Locator searchButton;
    public Locator cards;

    public ProductsPage(Page page) {
        this.searchInput = page.locator("//input[@placeholder='Search']");
        this.searchButton = page.locator("//button[contains(text(), 'Search')]");
        this.cards = page.locator("a.card");
    }
}
