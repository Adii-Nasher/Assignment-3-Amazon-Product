package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import util.TestContextSetup;
import java.io.IOException;

public class AmazonProductStepDef {

    TestContextSetup tcs;

    public AmazonProductStepDef(TestContextSetup tcs) {
        this.tcs = tcs;
    }

    @Given("I am able to search specified productId")
    public void i_am_able_to_search_specified_product_id() throws IOException {
        tcs.pom.getAmazon().clickSearchBar();
    }

    @When("I open the product page for specified productId")
    public void i_open_the_product_page_for_specified_product_id() throws IOException {
         tcs.pom.getAmazon().clickProduct();
    }

    @Then("I should see the Buy Now button")
    public void i_should_see_the_buy_now_button() throws IOException {
         tcs.pom.getAmazon().checkBuyNow();
    }

    @Then("I should check if customer rating for the product is over four")
    public void i_should_check_if_customer_rating_for_the_product_is_over_four() throws IOException {
         tcs.pom.getAmazon().checkCustomerRating();
    }

    @Then("Print all the offers available on the description page for the product")
    public void print_all_the_offers_available_on_the_description_page_for_the_product() throws IOException {
         tcs.pom.getAmazon().printAllOffers();
    }
}

