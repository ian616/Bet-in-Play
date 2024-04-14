package com.microbet.domain.game.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microbet.domain.game.service.GameService;
import com.microbet.domain.game.service.TeamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GameApiController {

    private final TeamService teamService;
    private final GameService gameService;

    @GetMapping("/api/v1/game")
    public void saveMemberV1(){
        teamService.scrapTeamInfo();
        gameService.scrapGameInfo();
    }
}
