package com.amazon.webautomation.pages;

import com.amazon.webautomation.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailPage {
    private WebDriver driver;

    public ProductDetailPage() {
        this.driver = DriverFactory.getDriver();
    }

    // Locators
    private By addToCartBtn = By.id("add-to-cart-button");
    private By productTitle = By.id("productTitle");
    private By productPrice = By.xpath("//span[@class='a-price-whole' or @id='priceblock_ourprice']");

    // Actions
    public void clickAddToCart() {
        driver.findElement(addToCartBtn).click();
    }

    // ✅ Validation Methods
    public boolean isProductTitleDisplayed() {
        return driver.findElements(productTitle).size() > 0 &&
                driver.findElement(productTitle).isDisplayed();
    }

    public boolean isPriceDisplayed() {
        return driver.findElements(productPrice).size() > 0 &&
                driver.findElement(productPrice).isDisplayed();
    }

    public String getProductTitle() {
        return driver.findElement(productTitle).getText().trim();
    }

    public String getProductPrice() {
        return driver.findElement(productPrice).getText().trim();
    }
}
