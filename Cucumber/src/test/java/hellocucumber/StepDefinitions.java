package hellocucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {

    WebDriver userDriver;
    WebDriver adminDriver;
    WebDriverWait userWait;
    WebDriverWait adminWait;

    String mainPageURL = "http://localhost:8080";
    String adminPageURL = "http://localhost:8080/admina";

    @Before("@User")
    public void userSetup() {
        userDriver = new ChromeDriver();
        userWait = new WebDriverWait(userDriver, Duration.ofSeconds(10));
    }

    @Before("@Admin")
    public void adminSetup() {
        adminDriver = new ChromeDriver();
        adminWait = new WebDriverWait(adminDriver, Duration.ofSeconds(10));
    }

    @After("User")
    public void tearDownUser() {
        if (adminDriver != null) {
            adminDriver.quit();
        }
    }

    @After("Admin")
    public void tearDownAdmin() {
        if (adminDriver != null) {
            adminDriver.quit();
        }
    }



    @Given("a customer navigates and login as {string} and {string}")
    public void theUserIsOnTheProductPage(String Username, String Password) {
        // Open the website and login
        userDriver.get(mainPageURL);
        userWait.until(ExpectedConditions.elementToBeClickable(By.id("_desktop_user_info"))).click();
        userWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-email"))).sendKeys(Username);
        userWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-password"))).sendKeys(Password);
        userWait.until(ExpectedConditions.elementToBeClickable(By.id("submit-login"))).click();
    }

    @When("the customer adds a product to their cart")
    public void customerAddsAProductToCart() throws InterruptedException {
        try {
            // Navigate to the product page and customize the product
            userWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                    "//a[@href='http://localhost:8080/home-accessories/19-customizable-mug.html']"))).click();
            userWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-textField1"))).sendKeys("I Love QA");
            userWait.until(ExpectedConditions.elementToBeClickable(By.name("submitCustomizedData"))).click();

            // Add to cart
            userWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'add-to-cart')]"))).click();
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            System.err.println("Thread sleep interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restore interrupt status
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @And("proceed to checkout")
    public void customerProceedToCheckout() throws InterruptedException {
        userWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@class, 'btn btn-primary') and contains(@href, 'cart?action=show')]"))).click();
        userWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='http://localhost:8080/order' and contains(@class, 'btn btn-primary')]"))).click();

        // Adding address
        WebElement addressField = userWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-address1")));
        addressField.clear();
        addressField.sendKeys("Los Angeles");

        WebElement cityField = userWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-city")));
        cityField.clear();
        cityField.sendKeys("Los Angeles");

        WebElement postField = userWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-postcode")));
        postField.clear();
        postField.sendKeys("12221");

        WebElement countryDropdown = userWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-id_country")));
        Select selectCountry = new Select(countryDropdown);
        selectCountry.selectByVisibleText("United States");

        WebElement stateDropdown = userWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-id_state")));
        Select selectState = new Select(stateDropdown);
        selectState.selectByVisibleText("California");


        // Continue to Delivery setting
        userWait.until(ExpectedConditions.elementToBeClickable(By.name("confirm-addresses"))).click();
        userWait.until(ExpectedConditions.elementToBeClickable(By.name("confirmDeliveryOption"))).click();

        // Agree to terms
        WebElement termsCheckbox = userWait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("conditions_to_approve[terms-and-conditions]")));

        JavascriptExecutor js = (JavascriptExecutor) userDriver;
        js.executeScript("arguments[0].click();", termsCheckbox);

        // Place the order
        userWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@type='submit' and @class='btn btn-primary center-block']"))).click();

    }

    @Then("the order is successfully placed")
    public void theOrderIsSuccessfullyPlaced() {
        try {
            // Wait for the order confirmation element to be visible
            WebElement orderConfirmation = userWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id='order-confirmation']")));

            // Assert that the order confirmation element is displayed
            assertTrue(orderConfirmation.isDisplayed(), "Order confirmation element should be displayed");

            System.out.println("Success: The order was successfully placed.");
        } catch (TimeoutException e) {
            System.err.println("Failure: The order confirmation element did not appear in time.");
            assertFalse(true, "Order confirmation element was not found. The order might not have been placed.");
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            assertFalse(true, "An unexpected error occurred while checking the order confirmation element.");
        }
    }



    /////////###############################################################/////////


    @Given("an admin logs into the admin panel as {string} and {string}")
    public void theUserAddsTheProductToTheCart(String Username, String Password) {
        // Open back office and admin login
        adminDriver.get(adminPageURL);
        adminWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys(Username);
        adminWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("passwd"))).sendKeys(Password);
        adminWait.until(ExpectedConditions.elementToBeClickable(By.id("submit_login"))).click();

    }


    @When("the admin changes the \"Date Available\" of the purchased product to a future date")
    public void theAdminChangesDateAvailable() {
        WebElement catalogMenu = adminWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@id='subtab-AdminCatalog']/a")));
        catalogMenu.click();
        adminWait.until(ExpectedConditions.elementToBeClickable(By.id("subtab-AdminProducts"))).click();
        adminWait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//a[@class='text-primary text-nowrap' and contains(@href, 'products-v2/19/edit')]"))).click();
        adminWait.until(ExpectedConditions.elementToBeClickable(By.id("product_stock-tab-nav"))).click();

        // Wait for the date input field to be visible
        WebElement dateField = getDateField();
        String date = getChangeToDate();

        ((JavascriptExecutor) adminDriver).executeScript(
                "arguments[0].value = arguments[1];", dateField, date);
        dateField.sendKeys("");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        adminWait.until(ExpectedConditions.elementToBeClickable(By.id("product_footer_save"))).click();
    }

    @Then("the product availability is updated successfully")
    public void theProductAvailabilityIsUpdatedSuccessfully() {
        // True value from website
        WebElement dateField = getDateField();
        String actualDate = dateField.getAttribute("value");

        // The expected value after changing
        String expectedDate = getChangeToDate();

        // Assert that the actual date matches the expected date
        assertEquals(actualDate, expectedDate, "Failed:::The product availability date does not match the expected date.");
        System.out.println("Success:::The product availability is updated successfully");
    }

    private WebElement getDateField(){
        return adminWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("product_stock_availability_available_date")));
    }

    private String getChangeToDate(){
        LocalDate targetDate = LocalDate.now().plusDays(3);
        return targetDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
