package com.akshar.framework.util;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotUtil {

    private ScreenshotUtil() {
    }

    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destinationDir = Paths.get("screenshots");
            if (!Files.exists(destinationDir)) {
                Files.createDirectories(destinationDir);
            }

            String fileName = testName.replaceAll("\\s+", "_") + "_" + System.currentTimeMillis() + ".png";
            Path destination = destinationDir.resolve(fileName);
            Files.copy(srcFile.toPath(), destination);
            return destination.toString();
        } catch (IOException e) {
            return "SCREENSHOT_CAPTURE_FAILED: " + e.getMessage();
        }
    }
}