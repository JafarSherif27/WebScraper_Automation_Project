package demo.wrappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.time.Duration;
import demo.locators.Locators;

//Helper methods
public class Wrappers {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    public static final String HOME_PAGE_URL = "https://www.scrapethissite.com/pages/";
    public final int MAX_WAIT_TIME = 20;

    // Constructor
    public Wrappers(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT_TIME));
        this.actions = new Actions(driver);

    }

    // To open a Url
    public void navigateTo(String url) {
        driver.get(url);

    }

    // To print Start and End test case logs
    public void logStatus(String description, String testCaseID) {
        String timeStamp = String.valueOf(java.time.LocalDateTime.now()).replace(":", "_");
        System.out.println(String.format("%s| %s | %s |", timeStamp, description, testCaseID));

    }

    // To print Error logs
    public void logErrors(String errorMessage, Exception e) {
        String timeStamp = String.valueOf(java.time.LocalDateTime.now()).replace(":", "_");
        System.out.println(String.format("%s| Error: %s |", timeStamp, errorMessage));
        e.printStackTrace();

    }

    // To Click on an element - using Locator
    public void clickOnElement(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            scrollIntoView(element);
            element.click();

            // Wait statement to load the page after clicking
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));

        } catch (Exception e) {
            logErrors("Failed to click on the given element " + locator, e);
        }

    }

    // To Click on an element - using WebElement
    public void clickOnElement(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            scrollIntoView(element);
            element.click();

            // Wait statement to load the page after clicking
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));

        } catch (Exception e) {
            logErrors("Failed to click on the given element ", e);
        }

    }

    // To scroll to an element
    public void scrollIntoView(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

        } catch (Exception e) {
            logErrors("Failed to scrol to the given element ", e);
        }

    }

    // To get the details of Hockey teams with win<40%(0.40) from first 4 pages
    public List<HashMap<String, Object>> getTeamDetails() {

        try {
            List<HashMap<String, Object>> hockeyTeamDetails = new ArrayList<>();

            // Getting details from 1st 4 pages
            for (int i = 1; i <= 4; i++) {
                clickOnElement(Locators.pageElement(i));

                // Get the WebElements of teams whoes win < 40%
                List<WebElement> winElements = getWinElements();
                for (WebElement ele : winElements) {
                    HashMap<String, Object> teamDetails = new HashMap<>();

                    long epoch = System.currentTimeMillis() / 1000;
                    double winPercent = Double.parseDouble(ele.getText().trim());

                    WebElement nameEle = ele.findElement(Locators.TEAM_NAME_ELEMENT);
                    String teamName = nameEle.getText().trim();

                    WebElement yearEle = ele.findElement(Locators.YEAR_ELEMENT);
                    int year = Integer.parseInt(yearEle.getText().trim());

                    teamDetails.put("Epoch", epoch);
                    teamDetails.put("Team Name", teamName);
                    teamDetails.put("Year", year);
                    teamDetails.put("Win %", winPercent);

                    hockeyTeamDetails.add(teamDetails);
                }
            }

            return hockeyTeamDetails;

        } catch (Exception e) {
            logErrors("Failed to get Hocky team details", e);
            return null;
        }

    }

    // To get the WebElemets of team with Win<40%(0.40)
    public List<WebElement> getWinElements() {
        try {
            List<WebElement> winElements = new ArrayList<>();

            List<WebElement> allWinElements = wait
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Locators.WIN_PERCENT_ELEMENT));

            for (WebElement ele : allWinElements) {
                double winPercent = Double.parseDouble(ele.getText().trim());

                if (winPercent < 0.40) {
                    winElements.add(ele);
                }
            }

            return winElements;

        } catch (Exception e) {
            logErrors("Failed to get Team elements with win% < 0.40", e);
            return null;
        }

    }

    // Convert List<HashMap<String,Object>> into JSON file
    public void covertToJson(List<HashMap<String, Object>> dataToConvert, String jsonFileName) {
        // Convert Java Object to JSON using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataToConvert);
            // System.out.println("jsonString: "+jsonString);

            // Create the "output" folder if it doesn't exist
            File outputFolder = new File("src/output");
            if (!outputFolder.exists()) {
                outputFolder.mkdir();
            }

            // Write JSON to a file inside "output" folder
            File jsonFile = new File("src/output/" + jsonFileName + ".json");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, dataToConvert);

            System.out.println(
                    "JSON file created successfully for " + jsonFileName + " at: " + jsonFile.getAbsolutePath());

        } catch (Exception e) {
            logErrors("Failed to convert JSON file", e);
        }

    }

    // To get the details of 1st 5 Oscar films from each years displayed
    public List<HashMap<String, Object>> getOscarFilmsDetails() {
        try {
            List<HashMap<String, Object>> filmsDetails = new ArrayList<>();
            List<String> years = new ArrayList<>();

            List<WebElement> yearElements = wait
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Locators.YEAR_LINKS_ELEMENT));

            for (WebElement ele : yearElements) {
                String yearText = ele.getText();
                years.add(yearText);
            }

            for (String year : years) {
                WebElement yearElement = wait
                        .until(ExpectedConditions.visibilityOfElementLocated(Locators.dynamicYearElement(year)));

                // Selects the year only if it is not selected
                if (!yearElement.getAttribute("class").contains("active")) {
                    clickOnElement(yearElement);
                    // waits for the page to load
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[text()='Title']")));
                }

                List<WebElement> titleElements = wait
                        .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Locators.MOVIE_TITLES_ELEMENT));

                for (int i = 0; i < 5; i++) {
                    HashMap<String, Object> movieDetails = new HashMap<>();
                    WebElement titleElement = titleElements.get(i);

                    String title = titleElement.getText();
                    long epoch = System.currentTimeMillis() / 1000;
                    boolean isWinner = i == 0;

                    String awards = titleElement.findElement(Locators.MOVIE_AWARDS_ELEMENT).getText();
                    String nomination = titleElement.findElement(Locators.MOVIE_NOMINATION_ELEMENT).getText();

                    movieDetails.put("Epoch", epoch);
                    movieDetails.put("Year", year);
                    movieDetails.put("Title", title);
                    movieDetails.put("Nomination", nomination);
                    movieDetails.put("Awards", awards);
                    movieDetails.put("isWinner", isWinner);

                    filmsDetails.add(movieDetails);
                }
            }

            return filmsDetails;

        } catch (Exception e) {
            logErrors("Failed while getting film details", e);
            return null;
        }

    }

}
