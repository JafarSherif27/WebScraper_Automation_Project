package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
// import dev.failsafe.internal.util.Assert;
import demo.locators.Locators;

public class TestCases {
    ChromeDriver driver;
    Wrappers wrappers;
    Locators locator;

    // Test
    @Test
    public void testCase01() {
        wrappers.logStatus("Start TestCase", "testCase01");

        wrappers.clickOnElement(Locators.HOCKEY_PAGE_ELEMENT);

        // To get the details of Hockey teams with win<40%(0.40) from first 4 pages
        List<HashMap<String, Object>> hockeyTeamDetails = wrappers.getTeamDetails();

        // Conver List<HashMap<String, Object>> into JSON FILE
        wrappers.covertToJson(hockeyTeamDetails, "hockey-team-data");

        wrappers.logStatus("End TestCase", "testCase01");
    }

    @Test
    public void testCase02() {
        wrappers.logStatus("Start TestCase", "testCase02");

        wrappers.clickOnElement(Locators.OSCAR_PAGE_ELEMENT);

        // To get the details of 1st 5 Oscar films from each years displayed
        List<HashMap<String, Object>> oscarFilmsDetails = wrappers.getOscarFilmsDetails();

        // Conver List<HashMap<String, Object>> into JSON FILE
        wrappers.covertToJson(oscarFilmsDetails, "oscar-winner-data");

        // Assert whether the File oscar-winner-data.json exists and is not empty
        File jsonFile = new File("src/output/oscar-winner-data.json");
        Assert.assertTrue(jsonFile.exists(), "The file does not exist");
        Assert.assertFalse(jsonFile.length() == 0, "The file is empty");

        wrappers.logStatus("End TestCase", "testCase02");
    }

    // Set-up -method level
    @BeforeMethod
    public void goToHomePage() {
        wrappers.navigateTo(Wrappers.HOME_PAGE_URL);
    }

    // Set-up -class level
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        this.wrappers = new Wrappers(driver);
        this.locator = new Locators();
    }

    // Tear down
    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}