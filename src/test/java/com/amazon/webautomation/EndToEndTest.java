package com.amazon.webautomation;

import com.amazon.webautomation.base.BaseTest;
import com.amazon.webautomation.config.ConfigReader;
import com.amazon.webautomation.dataproviders.JsonDataProvider;
import com.amazon.webautomation.pages.*;
import com.amazon.webautomation.utils.LogUtil;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class EndToEndTest extends BaseTest {

    @Test(groups = {"smoke", "sanity"},
            description = "Optionally login → Search → Add to Cart → Verify Cart",
            dataProvider = "jsonDataProvider",
            dataProviderClass = JsonDataProvider.class)
    public void verifySearchAndCartFlowWithOptionalLogin(Map<String, String> data) {

        String username = data.get("username"); // ✅ Get username from JSON data

        // 📂 Section: Navigation
        LogUtil.startSection("Navigation");
        driver.get(ConfigReader.get("baseUrl"));
        LogUtil.logStep("✅ Navigated to Amazon homepage");
        LogUtil.endSection();

        HomePage homePage = new HomePage();

        // 📂 Section: Login (optional)
        if (Boolean.parseBoolean(ConfigReader.get("loginRequired"))) {
            LogUtil.startSection("Login");
            LogUtil.logStep("🔐 loginRequired=true → Starting login flow");

            homePage.clickSignIn();
            LogUtil.logStep("➡️ Clicked on Sign In");

            LoginPage loginPage = new LoginPage();
            loginPage.login(username, ConfigReader.get("password"));
            LogUtil.logStep("🔓 Login completed with configured credentials.");
            LogUtil.endSection();
        } else {
            LogUtil.startSection("Login Skipped");
            LogUtil.logStep("⚠️ loginRequired=false → Skipping login.");
            LogUtil.endSection();
        }

        // 📂 Section: Search
        LogUtil.startSection("Search");
        homePage.enterSearchText("wireless headphones");
        LogUtil.logStep("🔎 Entered search text: 'wireless headphones'");

        homePage.clickSearchButton();
        LogUtil.logStep("🔍 Clicked search button");
        LogUtil.endSection();

        // 📂 Section: Product Detail
        LogUtil.startSection("Product Detail");
        SearchResultsPage resultsPage = new SearchResultsPage();
        resultsPage.clickFirstProduct();
        LogUtil.logStep("🧾 Opened first product in results");

        ProductDetailPage productPage = new ProductDetailPage();
        productPage.clickAddToCart();
        LogUtil.logStep("➕ Clicked Add to Cart");
        LogUtil.endSection();

        // 📂 Section: Cart Verification
        LogUtil.startSection("Cart Verification");
        CartPage cartPage = new CartPage();
        cartPage.openCart();
        LogUtil.logStep("🛒 Opened cart page");

        Assert.assertTrue(cartPage.isCartVisible(), "❌ Cart not visible!");
        LogUtil.logStep(Status.PASS, "✅ Cart page is visible. Test passed.");
        LogUtil.endSection();
    }
}
