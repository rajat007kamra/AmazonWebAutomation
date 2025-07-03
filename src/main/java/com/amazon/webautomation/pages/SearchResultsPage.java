package com.amazon.webautomation.pages;

import com.amazon.webautomation.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchResultsPage {
    private WebDriver driver;

    public SearchResultsPage() {
        this.driver = DriverFactory.getDriver();
    }

    private By resultItem = By.cssSelector("div.s-main-slot div[data-component-type='s-search-result']");

    public boolean isResultsDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            return wait.until(driver -> driver.findElements(resultItem).size() > 0);
        } catch (Exception e) {
            return false;
        }
    }

    public void clickFirstProduct() {
        driver.findElements(resultItem).get(0).click();
    }
}
