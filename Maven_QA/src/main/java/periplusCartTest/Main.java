package periplusCartTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    private static WebDriver driver;

    public static WebDriver setupDriver() {
//    	Define the Chrome Web driver
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Open browser in full screen
        return driver;
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser to clean the resources
        }
    }
}