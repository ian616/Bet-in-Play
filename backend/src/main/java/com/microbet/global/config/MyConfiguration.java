package com.microbet.global.config;


import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Configuration;

import com.microbet.domain.game.service.GameService;
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

    @PostConstruct
    public void init() {
        teamService.scrapTeamInfo();
        gameService.scrapGameInfo();
    }

    @PreDestroy
    public void onDestroy() {
        WebDriverUtil.quit();
    }
}
