package com.amazon.webautomation;

import com.amazon.webautomation.base.BaseTest;
import com.amazon.webautomation.config.ConfigReader;
import com.amazon.webautomation.pages.*;
import com.amazon.webautomation.utils.LogUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @Test(groups = {"regression","sanity"}, description = "Verify add to cart functionality")
    public void verifyAddToCartFlow() {
        driver.get(ConfigReader.get("baseUrl"));
        LogUtil.startSection("Search Product");
        HomePage homePage = new HomePage();
        homePage.enterSearchText("wireless mouse");
        homePage.clickSearchButton();
        LogUtil.endSection();

        LogUtil.startSection("Add to Cart");
        SearchResultsPage resultsPage = new SearchResultsPage();
        resultsPage.clickFirstProduct();

        ProductDetailPage productPage = new ProductDetailPage();
        productPage.clickAddToCart();
        LogUtil.logStep("✅ Product added to cart");
        LogUtil.endSection();

        LogUtil.startSection("Verify Cart");
        CartPage cartPage = new CartPage();
        cartPage.openCart();
        Assert.assertTrue(cartPage.isCartVisible(), "❌ Cart page not visible.");
        Assert.assertTrue(cartPage.isProductPresentInCart(), "❌ Product not found in cart.");
        LogUtil.logStep("✅ Product is present in cart");
        LogUtil.endSection();
    }
}
