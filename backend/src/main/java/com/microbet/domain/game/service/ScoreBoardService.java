package com.microbet.domain.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.microbet.domain.game.repository.GameRepository;
import com.microbet.domain.game.repository.TeamRepository;

import java.io.IOException;

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

        WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        try{
            HtmlPage page = webClient.getPage(baseURL);
            webClient.waitForBackgroundJavaScript(2000);

            HtmlElement scoreInfo = (HtmlElement) page.getFirstByXPath("//div[@class='info_score']");
            System.out.println(scoreInfo.getTextContent());

            webClient.close();

        }catch(IOException e){

        }
    }
}
