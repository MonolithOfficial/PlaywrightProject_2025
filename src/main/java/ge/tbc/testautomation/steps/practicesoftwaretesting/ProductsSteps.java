package ge.tbc.testautomation.steps.practicesoftwaretesting;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.practicesoftwaretesting.ProductsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProductsSteps {
    Page page;
    ProductsPage productsPage;

    public ProductsSteps(Page page) {
        this.page = page;
        this.productsPage = new ProductsPage(page);
    }

    public ProductsSteps searchForItem(String item){
        productsPage.searchInput.fill(item);
        productsPage.searchButton.click();

        return this;
    }

    public ProductsSteps validateNumberOfCards(int number){
        assertThat(productsPage.cards).hasCount(number);
        return this;
    }
}


