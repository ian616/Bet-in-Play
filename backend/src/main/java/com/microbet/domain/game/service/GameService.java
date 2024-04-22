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

        LocalDate currentDate = LocalDate.now().minusDays(3);

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
                // gameRepository.save(createGame(item));
                createGame(item);
            });

            webClient.close();
        } catch (IOException e) {

        }
    }

    private Game createGame(HtmlElement item) {
        // 다음 스포츠에서 home, away 팀을 반대로 표기함...미친놈들
        HtmlElement awayTeamElement = (HtmlElement) item.getFirstByXPath(".//div[contains(@class, 'team_home')]");
        HtmlElement homeTeamElement = (HtmlElement) item.getFirstByXPath(".//div[contains(@class, 'team_away')]");

        
        HtmlElement awayTeamScoreElement = (HtmlElement) awayTeamElement
                .getFirstByXPath(".//span[contains(@class, 'num_score')] | .//em[contains(@class, 'num_score')]");
        HtmlElement homeTeamScoreElement = (HtmlElement) homeTeamElement
                .getFirstByXPath(".//span[contains(@class, 'num_score')] | .//em[contains(@class, 'num_score')]");

        String awayTeamName = ((HtmlElement)awayTeamElement.getFirstByXPath(".//span[@class='txt_team']")).getTextContent();
        String homeTeamName = ((HtmlElement)homeTeamElement.getFirstByXPath(".//span[@class='txt_team']")).getTextContent();

        
        int awayTeamScore = Integer.parseInt(awayTeamScoreElement.getTextContent());
        int homeTeamScore = Integer.parseInt(homeTeamScoreElement.getTextContent());

        
        return Game.builder()
                .build();
    }
}
