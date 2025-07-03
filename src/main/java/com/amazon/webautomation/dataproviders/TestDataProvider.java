package com.amazon.webautomation.dataproviders;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "loginTestData")
    public static Object[][] searchData() {
        return new Object[][] {
                {"fiestuser@gmail.com"},
                {"seconduser@gmail.com"},
                {"thirduser@gmail.com"}
        };
    }
}
