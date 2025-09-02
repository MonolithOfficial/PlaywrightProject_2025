package ge.tbc.testautomation.steps.theinternet;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.theinternet.DynamicControlsPage;
import io.qameta.allure.Step;

public class DynamicControlsSteps {
    Page page;
    DynamicControlsPage dynamicControlsPage;

    public DynamicControlsSteps(Page page) {
        this.page = page;
        this.dynamicControlsPage = new DynamicControlsPage(page);
    }

    @Step("Click enable button")
    public DynamicControlsSteps clickEnableButton(){
        dynamicControlsPage.enableButton.click();

        return this;
    }

    @Step("Click disable button")
    public DynamicControlsSteps clickDisableButton(){
        dynamicControlsPage.disableButton.click();

        return this;
    }

    @Step("Validate message. Expecting {}")
    public DynamicControlsSteps validateMessage(String message){
        PlaywrightAssertions.assertThat(dynamicControlsPage.message).hasText(message);
        return this;
    }
}
