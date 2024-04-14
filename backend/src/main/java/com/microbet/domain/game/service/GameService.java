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

    private static final String baseUrl = "https://sports.daum.net/baseball";

    @Transactional
    public void scrapGameInfo() {

        // WebClient webClient = new WebClient(BrowserVersion.CHROME);
        // webClient.getOptions().setCssEnabled(false);
        // webClient.getOptions().setJavaScriptEnabled(true);
        // webClient.getOptions().setThrowExceptionOnScriptError(false);
        // webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        List<Team> teamList = teamRepository.findAll();
		List<Game> gameList = new ArrayList<>();

		// 두 개의 팀씩 묶어서 경기 생성
		for (int i = 0; i < teamList.size() - 1; i += 2) {
		    gameList.add(createGame(teamList.get(i), teamList.get(i + 1)));
		}

		gameList.forEach((game) -> {
		    // System.out.println("어웨이팀: " + game.getAwayTeam().toString());
		    // System.out.println("홈팀: " + game.getHomeTeam().toString());
		    gameRepository.save(game);
		});
    }

    private Game createGame(Team awayTeam, Team homeTeam) {

        return Game.builder()
                .awayTeam(awayTeam)
                .homeTeam(homeTeam)
                .build();
    }
}
