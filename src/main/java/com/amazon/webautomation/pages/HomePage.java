package com.amazon.webautomation.pages;

import com.amazon.webautomation.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;

    public HomePage() {
        this.driver = DriverFactory.getDriver();
    }

    // Locators
    private By searchBox = By.id("twotabsearchtextbox");
    private By searchButton = By.id("nav-search-submit-button");
    private By signInLink = By.id("nav-link-accountList");
    private By amazonLogo = By.id("nav-logo-sprites");

    private By todaysDealsLink = By.linkText("Today's Deals");  // Amazon.in
    private By todaysDealsHeader = By.xpath("//h1[contains(text(), \"Today's Deals\")]");

    private By regionSelector = By.id("icp-nav-flyout"); // Language/Region dropdown near top nav
    private By resultItem = By.cssSelector("div.s-main-slot div[data-component-type='s-search-result']");

    // Actions
    public void enterSearchText(String searchTerm) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(searchTerm);
    }

    public void clickSearchButton() {
        driver.findElement(searchButton).click();
    }

    public void clickSignIn() {
        driver.findElement(signInLink).click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void clickTodaysDeals() {
        driver.findElement(todaysDealsLink).click();
    }

    // ✅ Validation Methods
    public boolean isLogoDisplayed() {
        return driver.findElement(amazonLogo).isDisplayed();
    }

    public boolean isSearchBarDisplayed() {
        return driver.findElement(searchBox).isDisplayed();
    }

    public boolean isSignInLinkDisplayed() {
        return driver.findElement(signInLink).isDisplayed();
    }

    public boolean isTodaysDealsLinkPresent() {
        return driver.findElements(todaysDealsLink).size() > 0;
    }

    public boolean isTodaysDealsPageLoaded() {
        return driver.findElements(todaysDealsHeader).size() > 0;
    }

    public boolean isRegionSelectorVisible() {
        return driver.findElements(regionSelector).size() > 0 &&
                driver.findElement(regionSelector).isDisplayed();
    }
}
