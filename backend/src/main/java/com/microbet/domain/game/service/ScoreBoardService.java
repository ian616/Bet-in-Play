package com.microbet.domain.game.service;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microbet.domain.game.domain.ScoreBoard;
import com.microbet.domain.game.repository.GameRepository;
import com.microbet.domain.game.repository.TeamRepository;
import com.microbet.global.common.WebDriverUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScoreBoardService {
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void scrapScoreBoardInfo(Long daumGameId) {
        // ex) 	https://sports.daum.net/game/80078836/cast
        String baseURL = String.format("https://sports.daum.net/game/%d/cast", daumGameId);

        WebDriver driver = WebDriverUtil.getChromeDriver();
        
        driver.get(baseURL);
        WebElement castButton = driver.findElement(By.xpath("//li[contains(@data-menu, 'cast')]"));
        castButton.click();

        WebElement inningTab = driver.findElement(By.xpath("//ul[contains(@class, 'list_inning')]"));
        WebElement currentinning = inningTab.findElement(By.xpath(".//li[@class='on']/a[contains(@class, '#inning')]"));

        WebElement table = driver.findElement(By.xpath("//table[contains(@class, 'tbl_score')]/tbody"));

        createScoreBoard(table);

    }

    private void createScoreBoard(WebElement table){
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for(int i = 0; i < 2; i++){
            List<WebElement> scores = rows.get(i).findElements(By.tagName("td"));
            scores.forEach((score)->{
                System.out.println(score.getText());
            });
        }
    }
}
