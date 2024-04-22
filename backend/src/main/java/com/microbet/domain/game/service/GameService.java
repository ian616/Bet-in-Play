package com.microbet.domain.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ImmediateRefreshHandler;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.microbet.domain.game.domain.Game;
import com.microbet.domain.game.domain.Team;
import com.microbet.domain.game.enums.GameState;
import com.microbet.domain.game.enums.TeamName;
import com.microbet.domain.game.repository.GameRepository;
import com.microbet.domain.game.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    private static final String baseUrl = "https://sports.daum.net/schedule/kbo";

    @Transactional
    public void scrapGameInfo() {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        LocalDate currentDate = LocalDate.now().minusDays(2);

        // 달까지만 표시하는 형식
        DateTimeFormatter formatter_month = DateTimeFormatter.ofPattern("yyyyMM");
        String formattedDate_month = currentDate.format(formatter_month);

        // 날짜까지 표시하는 형식
        DateTimeFormatter formatter_date = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate_date = currentDate.format(formatter_date);

        String currentUrl = baseUrl + "?date=" + formattedDate_month;

        try {
            HtmlPage page = webClient.getPage(currentUrl);
            webClient.waitForBackgroundJavaScript(2000);

            String xpathExpr = String.format("//tr[contains(@data-date, %s)]", formattedDate_date);

            List<HtmlElement> gameList = page.getByXPath(xpathExpr);

            gameList.forEach((item) -> {
                gameRepository.save(createGame(item));
            });

            webClient.close();
        } catch (IOException e) {

        }
    }

    private Game createGame(HtmlElement item) {

        // ===html element에서 scrapping 하는 로직===//
        HtmlElement gameLinkElement = (HtmlElement) item.getFirstByXPath(".//a[contains(@class, 'link_game')]");

        HtmlElement awayTeamElement = (HtmlElement) item.getFirstByXPath(".//div[contains(@class, 'team_home')]");
        HtmlElement homeTeamElement = (HtmlElement) item.getFirstByXPath(".//div[contains(@class, 'team_away')]");

        HtmlElement gameStateElement = (HtmlElement) item.getFirstByXPath(".//span[@class='state_game ']");

        HtmlElement awayTeamScoreElement = (HtmlElement) awayTeamElement
                .getFirstByXPath(".//span[contains(@class, 'num_score')] | .//em[contains(@class, 'num_score')]");
        HtmlElement homeTeamScoreElement = (HtmlElement) homeTeamElement
                .getFirstByXPath(".//span[contains(@class, 'num_score')] | .//em[contains(@class, 'num_score')]");

        // ===scrapping한 값들을 실제 데이터화 하는 로직===//
        Long gameId = null;
        if(gameLinkElement != null){
            String[] gameLinkTokens = gameLinkElement.getAttribute("href").split("/");
            gameId = Long.parseLong(gameLinkTokens[gameLinkTokens.length - 1]);
        }
        
        String awayTeamAlias = ((HtmlElement) awayTeamElement.getFirstByXPath(".//span[@class='txt_team']"))
                .getTextContent();
        String homeTeamAlias = ((HtmlElement) homeTeamElement.getFirstByXPath(".//span[@class='txt_team']"))
                .getTextContent();

        Map<String, String> gameStateMap = Map.of(
                "경기전", "BEFORE_GAME",
                "경기중", "DURING_GAME",
                "종료", "AFTER_GAME",
                "경기취소", "CANCELED");

        String gameState = gameStateMap.get(gameStateElement.getTextContent());
        String startTime = ((HtmlElement) item.getFirstByXPath(".//td[@class='td_time']")).getTextContent();
        String location = ((HtmlElement) item.getFirstByXPath(".//td[@class='td_area']")).getTextContent().trim();
        String broadcasting = ((HtmlElement) item.getFirstByXPath(".//td[@class='td_tv']")).getTextContent();

        Integer awayTeamScore = awayTeamScoreElement.getTextContent().equals("-") ? null : Integer.parseInt(awayTeamScoreElement.getTextContent());
        Integer homeTeamScore = homeTeamScoreElement.getTextContent().equals("-") ? null : Integer.parseInt(homeTeamScoreElement.getTextContent());

        return Game.builder()
                .awayTeam(teamRepository.findByAlias(awayTeamAlias))
                .homeTeam(teamRepository.findByAlias(homeTeamAlias))
                .daumGameId(gameId)
                .gameState(GameState.valueOf(gameState))
                .startTime(startTime)
                .location(location)
                .broadcasting(broadcasting)
                .awayTeamScore(awayTeamScore)
                .homeTeamScore(homeTeamScore)
                .build();
    }
}
