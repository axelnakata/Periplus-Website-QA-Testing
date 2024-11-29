package periplusCartTest.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import periplusCartTest.Main;

public class ShoppingCartTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = Main.setupDriver();
    }

    @Test(priority = 1)
    public void testNavigateToHomePage() {
    	// Navigates to the Website URL
        driver.get("https://www.periplus.com/");
        
        // Validate the title of the page
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Periplus"), "Home page did not load successfully!");
    }

    @Test(priority = 2)
    public void testNavigateToSignIn() {
    	// Navigate to the "Sign In" element that is available
        WebElement signInLink = driver.findElement(By.xpath("//a[text()='Sign In']"));
        signInLink.click();
        
        // Validate by checking the title text that is displayed on the page
        WebElement signInTitle = driver.findElement(By.id("title-login"));
        Assert.assertTrue(signInTitle.isDisplayed(), "Sign In page did not load correctly!");
    }

    @Test(priority = 3)
    public void testFillLoginForm() throws InterruptedException {
        // Find the email and password fields
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));

        // Fill the login form with Dummy Registered Account
        emailField.sendKeys("ENTER DUMMY EMAIL HERE");
        passwordField.sendKeys("ENTER DUMMY PASS HERE");

        // Assert to validate that the email and password are correctly entered
        Assert.assertEquals(emailField.getAttribute("value"), "ENTER DUMMY EMAIL HERE", "Email input validation failed!");
        Assert.assertEquals(passwordField.getAttribute("value"), "ENTER DUMMY PASS HERE", "Password input validation failed!");

        // Find the login button and click it
        WebElement loginButton = driver.findElement(By.id("button-login"));
        loginButton.click();

        Thread.sleep(2000);  // Wait for 2 seconds for the login process

        // Post-login validation: Check if navigated to a page with the title "My Account"
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "My Account", "Post-login validation failed! Title did not change to 'My Account'.");
    }
    
    @Test(priority = 4)
    public void testSearchAndSelectProduct() throws InterruptedException {
    	// Locate the search bar and input "Atomic Habits" as Dummy Input
        WebElement searchBar = driver.findElement(By.id("filter_name"));
        searchBar.sendKeys("Atomic Habits");
        
        // Wait 3 seconds before Enter search, to cross-check the KEYS as the input
    	Thread.sleep(3000);

        // Press Enter to search
        searchBar.sendKeys(Keys.RETURN);

        Thread.sleep(2000);  // Wait for the search results to load

        // Validate that the search page is loaded correctly by checking the page title
        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Search"), "Search page title validation failed!");

        // Find and click on the "Atomic Habits" product card
        WebElement atomicHabitsLink = driver.findElement(By.xpath("//a[text()='Atomic Habits']"));
        atomicHabitsLink.click();

        Thread.sleep(2000);  // Wait for the product page to load

        // Validate that we are on the correct product page by checking the product title on the page
        WebElement productTitle = driver.findElement(By.xpath("//h2[text()='Atomic Habits']"));
        Assert.assertNotNull(productTitle, "Product page validation failed! Product title not found.");
    }
    
    @Test(priority = 5)
    public void testAddProductToCart() {
        // Verify the quantity field is set to 1 because we want to only add one product to the cart
        WebElement qtyField = driver.findElement(By.id("qty_54384253"));
        String qtyValue = qtyField.getAttribute("value");
        Assert.assertEquals(qtyValue, "1", "Quantity field is not set to 1");

        // Click on the "Add to Cart" button on the page
        WebElement addToCartButton = driver.findElement(By.cssSelector(".btn.btn-add-to-cart"));
        addToCartButton.click();
        
        // Wait for 2 seconds, until the "confirmation pop-up" is loaded
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        // Verify that the success pop-up appears with the text "Success add to cart"
        WebElement successModal = driver.findElement(By.cssSelector(".modal-text"));
        String successText = successModal.getText();
        Assert.assertEquals(successText, "Success add to cart", "Pop-up text is not as expected");

        // Wait for 5 seconds until the pop-up message dissapeared
        try {
            Thread.sleep(5000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the cart count has updated to 1, based on the "counter" on the cart icon
        WebElement cartCount = driver.findElement(By.id("cart_total"));
        String cartCountValue = cartCount.getText();
        Assert.assertEquals(cartCountValue, "1", "Cart count is not updated to 1 after adding product");
    }
    
    @Test(priority = 6)
    public void testProductInCart() {
    	// Click on the cart icon
        WebElement cartIcon = driver.findElement(By.cssSelector("#show-your-cart a.single-icon"));
        cartIcon.click();

        // Verify the title of the page is "Shopping Cart"
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Shopping Cart", "Page title is not correct!");

        // Wait for 6 seconds, making sure the cart page is loaded
        try {
            Thread.sleep(6000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Verify that "Atomic Habits" is present in the cart
        WebElement atomicHabitsProduct = driver.findElement(By.xpath("//a[text()='Atomic Habits']"));
        Assert.assertNotNull(atomicHabitsProduct, "Atomic Habits product is not present in the cart");
    }

    @AfterClass
    public void tearDown() {
        Main.tearDown();
    }
}