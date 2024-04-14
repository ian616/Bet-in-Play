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
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GameService {

    private static final String baseUrl = "https://sports.daum.net/baseball";

    public void scrapping() {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        try {
            HtmlPage page = webClient.getPage(baseUrl);

            webClient.waitForBackgroundJavaScript(2000);
            List<HtmlElement> teamList = page
                    .getByXPath("//div[contains(@class, 'team_left') or contains(@class, 'team_right')]");

            teamList.forEach((item) -> {
                inspectTeam(item);
            });

            webClient.close();
        } catch (IOException e) {

        }
    }

    private void inspectTeam(HtmlElement team) {
        HtmlElement teamNameElement = (HtmlElement) team.getFirstByXPath(".//span[@class='inner_tit']");
        HtmlElement teamScoreElement = (HtmlElement) team.getFirstByXPath(".//em[@class='num_score']");

        System.out.println("팀명: " + teamNameElement.getTextContent());
        System.out.println("득점: " + teamScoreElement.getTextContent());
    }
}
