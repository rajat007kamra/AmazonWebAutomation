package com.amazon.webautomation.pages;

import com.amazon.webautomation.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    public LoginPage() {
        this.driver = DriverFactory.getDriver();
    }

    private By emailInput = By.id("ap_email");
    private By continueBtn = By.id("continue");
    private By passwordInput = By.id("ap_password");
    private By signInBtn = By.id("signInSubmit");

    public void login(String email, String password) {
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(continueBtn).click();
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(signInBtn).click();
    }
}
