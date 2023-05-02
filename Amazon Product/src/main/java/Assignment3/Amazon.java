package Assignment3;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.Base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class Amazon {

    public WebDriver driver;

    public Base base;

    public WebDriverWait wait;

    @FindBy(css = "#twotabsearchtextbox") WebElement searchBox;

    @FindBy(css = "#nav-search-submit-button") WebElement searchButton;

    @FindBy(css = "span[id='submit.buy-now-announce']") WebElement buyNow;

    @FindBy(css="div[id='averageCustomerReviews_feature_div'] span[class='a-size-base a-color-base']") WebElement rating;

    By products = By.cssSelector("div[class='a-section a-spacing-base']");

    By offer = By.cssSelector("div[class='a-section vsx__offers multipleProducts']");

    public Amazon(WebDriver driver) {
        this.driver = driver;
        base = new Base();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void iWait(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void switchToNewWindow(WebDriver driver) {
        String currentWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            if (!window.equals(currentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }

    public void takeScreenshot(String methodName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileName = String.format("%s_%d.png", methodName, System.currentTimeMillis());
            String filePath = String.format("%s/screenshots/%s", System.getProperty("user.dir"), fileName);
            FileUtils.copyFile(screenshot, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void clickSearchBar() throws IOException {
        try {
            iWait(searchBox, 10);
            searchBox.click();
            String productId = System.getenv("INPUT_PRODUCT_ID");
            searchBox.sendKeys(productId);
            iWait(searchButton, 10);
            searchButton.click();
        } catch (WebDriverException e) {
            takeScreenshot("searchBar");
        }
    }

    public void clickProduct() {
        try {
            List<WebElement> elements = driver.findElements(products);
                WebElement Element = elements.get(0);
                Element.click();
        } catch (WebDriverException e) {
            takeScreenshot("clickProduct");
        }
    }

    public boolean checkBuyNow() {
        try {
            switchToNewWindow(driver);
            iWait(buyNow, 10);
            System.out.println("Buy button is displayed");
            return buyNow.isDisplayed();
        } catch (WebDriverException e) {
            takeScreenshot("Buy now");
        }
        return false;
    }


    public void checkCustomerRating() {
        try {
            switchToNewWindow(driver);
            if (rating.isDisplayed()) {
                // Get the text of the rating element and convert it to a float
                String ratingText = rating.getText();
                String numericRatingText = ratingText.replaceAll("[^\\d.]", "");
                float ratingValue = Float.parseFloat(numericRatingText);

                if (ratingValue > 4.0) {
                    System.out.println("This product has a customer rating over four!");
                } else {
                    System.out.println("This product does not have a customer rating over four.");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("This product does not have a customer rating.");
        }
    }

    public void printAllOffers() {
        try {
            switchToNewWindow(driver);
            List<WebElement> offersList = driver.findElements(offer);
            for (WebElement offer : offersList) {
                System.out.println(offer.getText());
            }
        } catch (WebDriverException e) {
            takeScreenshot("offers");
        }
    }
}
