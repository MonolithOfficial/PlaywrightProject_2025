package ge.tbc.testautomation;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadUploadTests {
    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false).setSlowMo(1000);

        browser = playwright.chromium().launch(launchOptions);
        context = browser.newContext();
        page = context.newPage();
        page.setDefaultTimeout(50000);
    }

    @Test
    public void testDownload() throws IOException {
        page.navigate("https://the-internet.herokuapp.com/download");

        Locator downloadLink = page.getByText("some-file.txt");

        Download download = page.waitForDownload(downloadLink::click);

        download.saveAs(Paths.get(System.getProperty("user.dir"), "/build/downloads/", download.suggestedFilename()));
        Path downloadedFile = download.path();
        String fileContent = Files.readString(downloadedFile);
        System.out.println(fileContent);
    }

    @Test
    public void testUpload() {
        page.navigate("https://the-internet.herokuapp.com/upload");

        Locator uploadInput = page.locator("input#file-upload");

        FileChooser fileChooser = page.waitForFileChooser(  uploadInput::click);

        if (fileChooser.isMultiple()){
            fileChooser
                    .setFiles(new Path[] {
                            Paths.get(System.getProperty("user.dir"), "/src/main/resources/ronaldokneeslide.jpg"),
                            Paths.get(System.getProperty("user.dir"), "/src/main/resources/ronaldokneeslide.jpg")
                    });
        }
        else {
            fileChooser.setFiles(Paths.get(System.getProperty("user.dir"), "/src/main/resources/ronaldokneeslide.jpg"));
        }
    }

    @AfterClass
    public void tearDown(){
        context.close();
        browser.close();
        playwright.close();
    }
}
