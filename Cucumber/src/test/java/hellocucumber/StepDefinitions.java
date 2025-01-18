package hellocucumber;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
//import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;

public class StepDefinitions {

    WebDriver driver = new ChromeDriver();
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


    @Given(" a customer navigates to the shop")
    public void theUserIsOnTheProductPage() {
        driver.get(mainPageURL);
        WebElement signUpButton = driver.findElement(By.id("_desktop_user_info"));
        signUpButton.click();
        try {
            driver.wait(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement emailField = driver.findElement(By.id("field-email"));
        emailField.sendKeys(userUserName);
        try {
            driver.wait(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        takeScreenshot();
    }

    @When("the customer adds a product to their cart and completes checkout")
    public void theUserAddsTheProductToTheCart() {
        // Find the 'Add to Cart' button and click it
        WebElement addToCartButton = driver.findElement(By.id("add-to-cart"));
        addToCartButton.click();
    }

    public void takeScreenshot() {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);  // Getting the screenshot as a file
        try {
            FileHandler.copy(source, new File("screenshot.png"));  // Saving the screenshot
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
