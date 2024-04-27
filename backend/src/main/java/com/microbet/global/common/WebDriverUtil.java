package com.microbet.global.common;

import java.time.Duration;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class WebDriverUtil {
    private static WebDriver driver;
    private final static String WEB_DRIVER_PATH = "/usr/local/bin/chromedriver"; // WebDriver 경로

    @PostConstruct
    public void init(){
        if (ObjectUtils.isEmpty(System.getProperty("webdriver.chrome.driver"))) {
            System.setProperty("webdriver.chrome.driver", WEB_DRIVER_PATH);
        }

        // webDriver 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--single-process");
        options.addArguments("--disable-dev-shm-usage");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        driver = new ChromeDriver(options);
        // driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    public static WebDriver getChromeDriver() {
        return driver;
    }

    public static void quit() {
        if (!ObjectUtils.isEmpty(driver)) {
            System.out.println("Shutdown Webdriver...");
            driver.quit();
        }
    }

    public static void close() {
        if (!ObjectUtils.isEmpty(driver)) {
            System.out.println("Close Webdriver...");
            driver.close();
        }
    }
}