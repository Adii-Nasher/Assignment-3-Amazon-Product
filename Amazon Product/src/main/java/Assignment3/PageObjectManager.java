package Assignment3;

import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class PageObjectManager {
    WebDriver driver;

    Amazon amazon;

    public PageObjectManager(WebDriver driver){
        this.driver=driver;
    }


    
    public Amazon getAmazon() throws IOException {
        amazon = new Amazon(driver);
        return amazon;
    }

}
