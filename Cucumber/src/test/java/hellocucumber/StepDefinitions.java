package hellocucumber;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
//import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StepDefinitions {

    WebDriver userDriver = new ChromeDriver();
    WebDriver adminDriver = new ChromeDriver();
    WebDriverWait userWait = new WebDriverWait(userDriver, Duration.ofSeconds(10));
    WebDriverWait adminWait = new WebDriverWait(adminDriver, Duration.ofSeconds(10));
    String mainPageURL = "http://localhost:8080";
    String adminPageURL = "http://localhost:8080/admina";
    String adminUserName = "demo@prestashop.com";
    String adminPassword = "prestashop_demo";
    String userUserName = "teamd@qa.com";
    String userPassword = "#TeamDTeamD#";


//    // $$*TODO* explain what this step does$$
//    @Given("an example scenario")
//    public void anExampleScenario() {
//        driver.get(mainPageURL);
//    }
//
//    // $$*TODO* explain what this step does$$
//    @When("all step definitions are implemented")
//    public void allStepDefinitionsAreImplemented() {
//    }
//
//    // $$*TODO* explain what this step does$$
//    @Then("the scenario passes")
//    public void theScenarioPasses() {
//    }


    @Given("a customer navigates to the shop")
    public void theUserIsOnTheProductPage() {
        try {
            // Open the website and login
            userDriver.get(mainPageURL);
            userWait.until(ExpectedConditions.elementToBeClickable(By.id("_desktop_user_info"))).click();
            userWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-email"))).sendKeys(userUserName);
            userWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-password"))).sendKeys(userPassword);
            userWait.until(ExpectedConditions.elementToBeClickable(By.id("submit-login"))).click();

            // Navigate to the product page and customize the product
            userWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                    "//a[@href='http://localhost:8080/home-accessories/19-customizable-mug.html']"))).click();
            userWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-textField1"))).sendKeys("I Love QA");
            userWait.until(ExpectedConditions.elementToBeClickable(By.name("submitCustomizedData"))).click();

            // Add to cart and proceed to checkout
            userWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'add-to-cart')]"))).click();
            Thread.sleep(1000);
            userWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@class, 'btn btn-primary') and contains(@href, 'cart?action=show')]"))).click();
            userWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[@href='http://localhost:8080/order' and contains(@class, 'btn btn-primary')]"))).click();

        } catch (TimeoutException e) {
            System.err.println("Timeout occurred: Unable to locate element within the specified time. Check the locator or page load speed.");
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            System.err.println("Element not found: Ensure the locator is correct and the element is present on the page.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Thread sleep interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restore interrupt status
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @When("the customer adds a product to their cart and completes checkout")
    public void theUserAddsTheProductToTheCart() {
        // Open back office and admin login
        adminDriver.get(adminPageURL);
        adminWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys(adminUserName);
        adminWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("passwd"))).sendKeys(adminPassword);
        adminWait.until(ExpectedConditions.elementToBeClickable(By.id("submit_login"))).click();

        WebElement catalogMenu = adminWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@id='subtab-AdminCatalog']/a")));
        catalogMenu.click();
        adminWait.until(ExpectedConditions.elementToBeClickable(By.id("subtab-AdminProducts"))).click();
        adminWait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//a[@class='text-primary text-nowrap' and contains(@href, 'products-v2/19/edit')]"))).click();
        adminWait.until(ExpectedConditions.elementToBeClickable(By.id("product_stock-tab-nav"))).click();

        LocalDate targetDate = LocalDate.now().plusDays(3);
        String formattedDate = targetDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Wait for the date input field to be visible
        WebElement dateField = adminWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("product_stock_availability_available_date")));

        // Clear the existing value and input the new date
        dateField.clear();
        dateField.sendKeys(formattedDate);

        adminWait.until(ExpectedConditions.elementToBeClickable(By.id("product_footer_save"))).click();

    }

//    @When("")
//
//    @When("the admin changes the Date Available of the purchased product to a future date")
//    public void theAdminChangesDateAvailable() {
//        userDriver.get(adminPageURL);
//    }
//


}
