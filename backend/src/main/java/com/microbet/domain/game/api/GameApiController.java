package com.microbet.domain.game.api;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microbet.domain.game.domain.LiveCast;
import com.microbet.domain.game.dto.LiveCastDTO;
import com.microbet.domain.game.service.GameService;
import com.microbet.domain.game.service.LiveCastService;
import com.microbet.domain.game.service.ScoreBoardService;
import com.microbet.domain.game.service.TeamService;
import com.microbet.global.common.WebDriverUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GameApiController {

    private final LiveCastService liveCastService;

    @GetMapping("/api/v1/game")
    public void getGameList() {

    }

    @GetMapping("/api/v1/game/{id}")
    public List<LiveCastDTO.Response> getLiveCast() {
        List<LiveCast> liveCastResults = liveCastService.findLiveCasts();
        return liveCastResults.stream()
                .map(liveCast -> new LiveCastDTO.Response(liveCast)).collect(Collectors.toList());
    }
}
