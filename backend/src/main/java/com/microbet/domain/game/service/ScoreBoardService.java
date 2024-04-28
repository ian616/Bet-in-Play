package com.microbet.domain.game.service;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microbet.domain.game.domain.Game;
import com.microbet.domain.game.domain.ScoreBoard;
import com.microbet.domain.game.repository.GameRepository;
import com.microbet.domain.game.repository.ScoreBoardRepository;
import com.microbet.domain.game.repository.TeamRepository;
import com.microbet.global.common.WebDriverUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScoreBoardService {
    private final GameRepository gameRepository;
    private final ScoreBoardRepository scoreBoardRepository;

    @Transactional
    public void scrapScoreBoardInfo(Long id) {

        Game game = gameRepository.findById(id);

        String baseURL = String.format("https://sports.daum.net/game/%d/cast", game.getDaumGameId());

        WebDriver driver = WebDriverUtil.getChromeDriver();

        driver.get(baseURL);
        // WebElement castButton =
        // driver.findElement(By.xpath("//li[contains(@data-menu, 'cast')]"));
        // castButton.click();

        // WebElement inningTab = driver.findElement(By.xpath("//ul[contains(@class,
        // 'list_inning')]"));
        // WebElement currentInning =
        // inningTab.findElement(By.xpath(".//li[@class='on']/a[contains(@class,
        // '#inning')]"));

        List<WebElement> tables = driver.findElements(By.xpath("//table[contains(@class, 'tbl_score')]/tbody"));

        scoreBoardRepository.save(createScoreBoard(tables.get(0), tables.get(1), game));
    }

    private ScoreBoard createScoreBoard(WebElement scoreTable, WebElement infoTable, Game game) {
        List<Integer> awayTeamScores = new ArrayList<>();
        List<Integer> homeTeamScores = new ArrayList<>();

        List<WebElement> scoreRows = scoreTable.findElements(By.tagName("tr"));
        List<WebElement> infoRows = infoTable.findElements(By.tagName("tr"));

        // Away Team
        List<WebElement> awayTeamScoresElements = scoreRows.get(0).findElements(By.tagName("td"));

        awayTeamScoresElements.forEach((score) -> {
            String scoreString = score.getText();
            if (scoreString != "") {
                awayTeamScores.add(Integer.parseInt(scoreString));
            }
        });

        List<WebElement> awayTeamInfosElements = infoRows.get(0).findElements(By.tagName("td"));
        List<Integer> awayTeamInfos = new ArrayList<>();

        awayTeamInfosElements.forEach((info) -> {
            String scoreString = info.getText();
            if (scoreString == "") {
                awayTeamInfos.add(null);
            }else{
                awayTeamInfos.add(Integer.parseInt(scoreString));
            }
        });
        System.out.println(awayTeamInfos.toString());

        // Home Team
        List<WebElement> homeTeamScoresElements = scoreRows.get(1).findElements(By.tagName("td"));

        homeTeamScoresElements.forEach((score) -> {
            String scoreString = score.getText();
            if (scoreString != "") {
                homeTeamScores.add(Integer.parseInt(scoreString));
            }

        });

        List<WebElement> homeTeamInfosElements = infoRows.get(1).findElements(By.tagName("td"));
        List<Integer> homeTeamInfos = new ArrayList<>();

        homeTeamInfosElements.forEach((info) -> {
            String scoreString = info.getText();
            if (scoreString == "") {
                homeTeamInfos.add(null);
            }else{
                homeTeamInfos.add(Integer.parseInt(scoreString));
            }
        });
        System.out.println(homeTeamInfos.toString());

        return ScoreBoard.builder()
                .game(game)
                .awayTeamScores(awayTeamScores)
                .homeTeamScores(homeTeamScores)
                .awayTeamScore(awayTeamInfos.get(0))
                .homeTeamScore(homeTeamInfos.get(0))
                .awayTeamHit(awayTeamInfos.get(1))
                .homeTeamHit(homeTeamInfos.get(1))
                .awayTeamError(awayTeamInfos.get(2))
                .homeTeamError(homeTeamInfos.get(2))
                .awayTeamBB(awayTeamInfos.get(3))
                .homeTeamBB(homeTeamInfos.get(3))
                .build();
    }
}
