package com.microbet.domain.game.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microbet.domain.game.domain.Game;
import com.microbet.domain.game.repository.GameRepository;
import com.microbet.domain.game.repository.TeamRepository;
import com.microbet.global.common.WebDriverUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LiveCastService {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void scrapLiveCast(Long id) {
        Game game = gameRepository.findById(id);

        String baseURL = String.format("https://sports.daum.net/game/%d/cast", game.getDaumGameId());
        WebDriver driver = WebDriverUtil.getChromeDriver();

        driver.get(baseURL);

        WebElement inningTab = driver.findElement(By.xpath("//ul[contains(@class,'list_inning')]"));
        WebElement currentInning = inningTab.findElement(By.xpath(".//li[@class='on']/a[contains(@class,'#inning')]"));
        System.out.println(currentInning.getText());
        System.out.println("성공!!!");
    }
}
