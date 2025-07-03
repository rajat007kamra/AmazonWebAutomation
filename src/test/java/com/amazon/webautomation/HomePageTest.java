package com.amazon.webautomation;

import com.amazon.webautomation.base.BaseTest;
import com.amazon.webautomation.config.ConfigReader;
import com.amazon.webautomation.pages.HomePage;
import com.amazon.webautomation.pages.SearchResultsPage;
import com.amazon.webautomation.utils.LogUtil;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test(groups = {"sanity"}, description = "Verify homepage elements like logo and search bar")
    public void verifyHomePageElements() {
        driver.get(ConfigReader.get("baseUrl"));
        LogUtil.startSection("Home Page Validation");

        HomePage homePage = new HomePage();

        Assert.assertTrue(homePage.isLogoDisplayed(), "❌ Amazon logo not visible.");
        LogUtil.logStep(Status.PASS, "✅ Amazon logo is visible", true);

        Assert.assertTrue(homePage.isSearchBarDisplayed(), "❌ Search bar is not visible.");
        LogUtil.logStep(Status.PASS, "✅ Search bar is visible", true);

        LogUtil.endSection();
    }

    @Test(groups = {"sanity"}, description = "Verify product search functionality")
    public void verifyProductSearchFunctionality() {
        driver.get(ConfigReader.get("baseUrl"));
        LogUtil.startSection("Search Functionality");

        HomePage homePage = new HomePage();
        homePage.enterSearchText("laptop");
        LogUtil.logStep(Status.INFO, "Entered product name: laptop", false);

        homePage.clickSearchButton();
        LogUtil.logStep(Status.INFO, "Clicked on search button", true);

        SearchResultsPage resultsPage = new SearchResultsPage();
        Assert.assertTrue(resultsPage.isResultsDisplayed(), "❌ Search results not displayed.");
        LogUtil.logStep(Status.PASS, "✅ Search results are displayed", true);

        LogUtil.endSection();
    }

    @Test(groups = {"regression"}, description = "Verify Today's Deals link navigation")
    public void verifyTodaysDealsNavigation() {
        driver.get(ConfigReader.get("baseUrl"));
        LogUtil.startSection("Today's Deals Navigation");

        HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.isTodaysDealsLinkPresent(), "❌ Today's Deals link not found.");
        LogUtil.logStep(Status.INFO, "✅ Today's Deals link is present", true);

        homePage.clickTodaysDeals();
        LogUtil.logStep(Status.INFO, "Clicked on Today's Deals", true);

        Assert.assertTrue(homePage.isTodaysDealsPageLoaded(), "❌ Today's Deals page did not load properly.");
        LogUtil.logStep(Status.PASS, "✅ Navigated to Today's Deals successfully", true);

        LogUtil.endSection();
    }

    @Test(groups = {"regression"}, description = "Verify language selector or region dropdown is visible")
    public void verifyLanguageOrRegionSelector() {
        driver.get(ConfigReader.get("baseUrl"));
        LogUtil.startSection("Language / Region Selector");

        HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.isRegionSelectorVisible(), "❌ Region selector not visible.");
        LogUtil.logStep(Status.PASS, "✅ Region or language selector is visible", true);

        LogUtil.endSection();
    }
}
