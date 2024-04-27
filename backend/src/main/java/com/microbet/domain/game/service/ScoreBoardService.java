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

        WebElement table = driver.findElement(By.xpath("//table[contains(@class, 'tbl_score')]/tbody"));

        scoreBoardRepository.save(createScoreBoard(table, game));
    }

    private ScoreBoard createScoreBoard(WebElement table, Game game) {
        List<Integer> awayTeamScores = new ArrayList<>();
        List<Integer> homeTeamScores = new ArrayList<>();

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        // Away Team
        List<WebElement> awayTeamScoresElements = rows.get(0).findElements(By.tagName("td"));

        awayTeamScoresElements.forEach((score) -> {
            String scoreString = score.getText();
            if (scoreString != "") {
                awayTeamScores.add(Integer.parseInt(scoreString));
            }
        });

        // Home Team
        List<WebElement> homeTeamScoresElements = rows.get(1).findElements(By.tagName("td"));

        homeTeamScoresElements.forEach((score) -> {
            String scoreString = score.getText();
            if (scoreString == "") {
                homeTeamScores.add(null);
            } else {
                homeTeamScores.add(Integer.parseInt(scoreString));
            }

        });

        return ScoreBoard.builder()
                .game(game)
                .awayTeamScores(awayTeamScores)
                .homeTeamScores(homeTeamScores)
                // .awayTeamScore(awayTeamScores.get(awayTeamScores.size() - 1))
                // .homeTeamScore(homeTeamScores.get(homeTeamScores.size() - 1))
                .build();
    }
}
