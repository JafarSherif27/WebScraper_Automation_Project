package demo.locators;

import org.openqa.selenium.By;

public class Locators {

    /*
     * Locators related to Hocky page -testCase01
     */

    // Locates Hyper link of Hockey Teams page
    public static final By HOCKEY_PAGE_ELEMENT = By.xpath("//a[contains(@href, 'forms')]");

    // Locates the pagination element
    public static By pageElement(int pageNo) {
        return By.xpath("//a[contains(@href,'page_num') and normalize-space(text())='" + pageNo + "']");
    }

    // Locates all Win% element from the current page
    public static final By WIN_PERCENT_ELEMENT = By.xpath("//tr/td[contains(@class,'pct text')]");

    // Locates Team Name -To use this locator parent element of Win % is required
    public static final By TEAM_NAME_ELEMENT = By.xpath(".//preceding-sibling::td[@class='name']");

    // Locates Year of match -To use this locator parent element of Win % is
    // required
    public static final By YEAR_ELEMENT = By.xpath(".//preceding-sibling::td[@class='year']");

    /*
     * Locators related to Oscar Winning Films -testCase02
     */

    // Locates Hyper link of Oscar Winning Films page
    public static final By OSCAR_PAGE_ELEMENT = By.xpath("//a[contains(@href, 'ajax')]");

    // Locates all the Year links available
    public static final By YEAR_LINKS_ELEMENT = By.xpath("//a[contains(@href,'#') and @class='year-link']");

    // Locates Year link dynamically based on the given year
    public static By dynamicYearElement(String year) {
        return By.xpath("//a[contains(@href,'#') and  contains(text(),'" + year + "')]");
    }

    // Locates all Movie Title Elements
    public static final By MOVIE_TITLES_ELEMENT = By.xpath("//td[@class='film-title']");

    // Locates Movie Nomination element -To use this locator parent element of Movie
    // Title Element is required
    public static final By MOVIE_NOMINATION_ELEMENT = By.xpath(".//following-sibling::td[@class='film-nominations']");

    // Locates Movie Awards element -To use this locator parent element of Movie
    // Title Element is required
    public static final By MOVIE_AWARDS_ELEMENT = By.xpath(".//following-sibling::td[@class='film-awards']");

}
