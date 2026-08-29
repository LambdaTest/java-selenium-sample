package com.lambdatest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SmartUI {
    public static String hubURL = "https://hub.lambdatest.com/wd/hub";
    private WebDriver driver;

    public void setup() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        Map<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("user", System.getenv("LT_USERNAME"));
        ltOptions.put("accessKey", System.getenv("LT_ACCESS_KEY"));
        ltOptions.put("build", "SmartUI-Java-Sample");
        ltOptions.put("name", this.getClass().getName());
        ltOptions.put("smartUI.project","Java-Selenium");
        if(System.getenv("BUILD_NAME")!=null && System.getenv("BUILD_NAME")!=""){
            ltOptions.put("smartUI.build",System.getenv("BUILD_NAME"));
        }
        capabilities.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL(hubURL), capabilities);
        System.out.println(driver);
    }

    public void captureSnapshot() {
        driver.get("https://www.lambdatest.com/");

        Map<String, Object> config = new HashMap<>();
        config.put("screenshotName","lambdatest"); //Add your snapshot name here for SmartUI
        ((JavascriptExecutor)driver).executeScript("smartui.takeScreenshot",config); //Hook for capturing snapshot on SmartUI
    }

    public void tearDown() {
        try {
            driver.quit();
        } catch (

        Exception e) {
            e.printStackTrace();
            driver.quit();
        }
    }

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        SmartUI test = new SmartUI();
        test.setup();
        test.captureSnapshot();
        test.tearDown();
    }
}
