package com.microbet.domain.game.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microbet.domain.game.service.GameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GameApiController {
    private final GameService gameService;

    @GetMapping("/api/v1/game")
    public void saveMemberV1(){
        gameService.scrapGameInfo();
    }
}
