package com.microbet.global.config;


import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.microbet.domain.game.service.GameService;
import com.microbet.domain.game.service.LiveCastService;
import com.microbet.domain.game.service.ScoreBoardService;
import com.microbet.domain.game.service.TeamService;
import com.microbet.domain.quiz.domain.AnswerOption;
import com.microbet.domain.quiz.domain.Question;
import com.microbet.domain.quiz.service.AnswerOptionService;
import com.microbet.domain.quiz.service.QuestionService;
import com.microbet.global.common.WebDriverUtil;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MyConfiguration {

    private final TeamService teamService;
    private final GameService gameService;
    private final LiveCastService liveCastService;
    
    private final AnswerOptionService answerOptionService;

    @PostConstruct
    public void init() {
        // 게임 정보 초기화
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
