package ge.tbc.testautomation.steps.theinternet;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.theinternet.DynamicControlsPage;

public class DynamicControlsSteps {
    Page page;
    DynamicControlsPage dynamicControlsPage;

    public DynamicControlsSteps(Page page) {
        this.page = page;
        this.dynamicControlsPage = new DynamicControlsPage(page);
    }

    public DynamicControlsSteps clickEnableButton(){
        dynamicControlsPage.enableButton.click();

        return this;
    }

    public DynamicControlsSteps clickDisableButton(){
        dynamicControlsPage.disableButton.click();

        return this;
    }

    public DynamicControlsSteps validateMessage(String message){
        PlaywrightAssertions.assertThat(dynamicControlsPage.message).hasText(message);
        return this;
    }
}
