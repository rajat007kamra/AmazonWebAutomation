package com.amazon.webautomation;

import com.amazon.webautomation.base.BaseTest;
import com.amazon.webautomation.config.ConfigReader;
import com.amazon.webautomation.pages.*;
import com.amazon.webautomation.utils.LogUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductDetailPageTest extends BaseTest {

    @Test(groups = {"sanity"}, description = "Verify product title and price on product detail page")
    public void verifyProductDetails() {
        driver.get(ConfigReader.get("baseUrl"));
        LogUtil.startSection("Navigate and Search");
        HomePage homePage = new HomePage();
        homePage.enterSearchText("earphones");
        homePage.clickSearchButton();
        LogUtil.endSection();

        LogUtil.startSection("Open Product");
        SearchResultsPage searchResultsPage = new SearchResultsPage();
        searchResultsPage.clickFirstProduct();
        LogUtil.endSection();

        LogUtil.startSection("Verify Product Details");
        ProductDetailPage productPage = new ProductDetailPage();

        Assert.assertTrue(productPage.isProductTitleDisplayed(), "❌ Product title is missing");
        LogUtil.logStep("✅ Product title is visible");

        Assert.assertTrue(productPage.isPriceDisplayed(), "❌ Product price is missing");
        LogUtil.logStep("✅ Product price is visible");
        LogUtil.endSection();
    }
}
