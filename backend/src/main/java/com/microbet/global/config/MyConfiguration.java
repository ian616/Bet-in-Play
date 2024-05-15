package com.microbet.global.config;


import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.microbet.domain.game.service.GameService;
import com.microbet.domain.game.service.LiveCastService;
import com.microbet.domain.game.service.ScoreBoardService;
import com.microbet.domain.game.service.TeamService;
import com.microbet.global.common.WebDriverUtil;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MyConfiguration {

    private final TeamService teamService;
    private final GameService gameService;
    private final LiveCastService liveCastService;

    @PostConstruct
    public void init() {
        System.out.println("start scrapping...");
        System.out.println("scrapping team information...");
        teamService.scrapTeamInfo();
        System.out.println("...team information scrapped successfully.");
        System.out.println("scrapping game information...");
        gameService.scrapGameInfo();
        System.out.println("...game information scrapped successfully.");
        System.out.println("...scrapping complete.");
        liveCastService.initLiveCast();
    }
}
