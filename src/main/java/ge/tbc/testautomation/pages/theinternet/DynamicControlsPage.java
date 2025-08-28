package ge.tbc.testautomation.pages.theinternet;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DynamicControlsPage {
    public Locator enableButton;
    public Locator disableButton;
    public Locator message;

    public DynamicControlsPage(Page page){
        enableButton = page.locator("button").getByText("Enable");
        disableButton = page.locator("button").getByText("Disable");
        message = page.locator("p#message");
    }
}
