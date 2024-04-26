package com.microbet.domain.game.service;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microbet.domain.game.repository.GameRepository;
import com.microbet.domain.game.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScoreBoardService {
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void scrapScoreBoardInfo(Long daumGameId) {
        String baseURL = String.format("https://sports.daum.net/game/%d/cast", daumGameId);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--single-process");
        options.addArguments("--disable-dev-shm-usage");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        WebDriver driver = new ChromeDriver(options);

        driver.get(baseURL);
    }
}
