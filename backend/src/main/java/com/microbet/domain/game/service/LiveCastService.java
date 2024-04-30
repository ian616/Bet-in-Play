package com.microbet.domain.game.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
        WebElement currentInningElement = inningTab
                .findElement(By.xpath(".//li[@class='on']/a[contains(@class,'#inning')]"));

        int currentInning = Character.getNumericValue(currentInningElement.getText().charAt(0));

        List<WebElement> playerCastTextElement = driver
                .findElements(By.xpath(String.format(
                        "//div[@class='sms_list ' and @data-inning='%d']/div[@class='item_sms ']", currentInning)));

        playerCastTextElement.forEach((playerCast) -> {
            // 플레이어 정보 스크래핑
            try {
                WebElement playerElement = playerCast.findElement(By.xpath(".//div[@class='info_player']"));
                WebElement playerTextElement = playerElement.findElement(By.xpath(".//div[@class='cont_info']"));
                WebElement playerImageElement = playerElement
                        .findElement(By.xpath(".//span[contains(@class, 'thumb_round')]/img"));

                String playerImageURL = playerImageElement.getAttribute("src");

                String playerText = playerTextElement.getText();
                String pattern = "^(.*?) (\\d+)번타자 \\(No\\.(\\d+)\\)$";

                Pattern regex = Pattern.compile(pattern);
                Matcher matcher = regex.matcher(playerText);

      
                String name = matcher.group(1);
                int battingOrder = Integer.parseInt(matcher.group(2));
                int backNumber = Integer.parseInt(matcher.group(3));

                Player player = Player.builder()
                                    .name(name)
                                    .battingOrder(battingOrder)
                                    .backNumber(backNumber)
                                    .playerImageURL(playerImageURL)
                                    .build();
                // 문자 중계 스크래핑
                List<WebElement> pureCastTextElements = playerCast
                        .findElements(By.xpath(".//em[@class='sms_word ']"));
                List<String> currentText = pureCastTextElements.stream().map(WebElement::getText).toList();
                LiveCast livecast = createLiveCast(game, player, currentText);

                System.out.println(livecast.getCurrentText());
                System.out.println(livecast.getPlayer().toString());

            } catch (NoSuchElementException e) {

            }

        });

    }

    private LiveCast createLiveCast(Game game, Player player, List<String> currentText) {
        return LiveCast.builder()
                .game(game)
                .currentText(currentText)
                .player(player)
                .build();
    }
}
