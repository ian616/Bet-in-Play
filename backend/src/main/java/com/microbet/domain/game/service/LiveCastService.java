package com.microbet.domain.game.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microbet.domain.game.domain.Game;
import com.microbet.domain.game.domain.LiveCast;
import com.microbet.domain.game.embeddable.Player;
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
        WebElement currentInningElement = inningTab.findElement(By.xpath(".//li[@class='on']/a[contains(@class,'#inning')]"));

        
        int currentInning = Character.getNumericValue(currentInningElement.getText().charAt(0));
        
        List<WebElement> inningCastTextElements = driver.findElements(By.xpath(String.format("//div[@class='sms_list ' and @data-inning='%d']", currentInning)));
        inningCastTextElements.forEach((inning)->{
            List<WebElement> pureCastTextElements = inning.findElements(By.xpath(".//em[@class='sms_word ']"));
            List<String> currentText = pureCastTextElements.stream().map(WebElement::getText).toList();
        });
    }

    private void createLiveCast(Game game, Player player, List<String> currentText){

    }
}
