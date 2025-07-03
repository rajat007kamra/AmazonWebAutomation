package com.amazon.webautomation.pages;

import com.amazon.webautomation.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage {
    private WebDriver driver;

    public CartPage() {
        this.driver = DriverFactory.getDriver();
    }

    // Locators
    private By cartIcon = By.id("nav-cart");
    private By cartHeader = By.xpath("//h1[contains(text(), 'Shopping Cart')]");
    private By cartItems = By.xpath("//div[contains(@class, 'sc-list-item-content') or contains(@data-name,'Active Items')]");

    // Actions
    public void openCart() {
        driver.findElement(cartIcon).click();
    }

    // Validations
    public boolean isCartVisible() {
        return driver.findElement(cartHeader).isDisplayed();
    }

    public boolean isProductPresentInCart() {
        List<WebElement> items = driver.findElements(cartItems);
        return items.size() > 0;
    }

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }
}
