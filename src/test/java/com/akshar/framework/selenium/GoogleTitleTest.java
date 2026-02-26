package com.akshar.framework.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoogleTitleTest {

    private WebDriver driver;

    @Test
    void googleTitleContainsGoogle() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.get("https://www.google.com");
        String title = driver.getTitle();

        assertTrue(title.toLowerCase().contains("google"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}