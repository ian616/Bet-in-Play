package com.microbet.domain.game.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microbet.domain.game.service.GameService;
import com.microbet.domain.game.service.ScoreBoardService;
import com.microbet.domain.game.service.TeamService;
import com.microbet.global.common.WebDriverUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GameApiController {

    private final GameService gameService;
    private final ScoreBoardService scoreBoardService;

    @GetMapping("/api/v1/game")
    public void getGameList() {
        gameService.scrapGameInfo();
    }
    
    @GetMapping("/api/v1/game/{id}")
    public void getScoreBoard(@RequestParam Long gameId){
        scoreBoardService.scrapScoreBoardInfo(gameId);
    }
}
