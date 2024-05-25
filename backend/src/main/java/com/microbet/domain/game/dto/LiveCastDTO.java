package com.microbet.domain.game.dto;

import java.util.List;

import com.microbet.domain.game.domain.Game;
import com.microbet.domain.game.domain.LiveCast;
import com.microbet.domain.game.embeddable.Player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class LiveCastDTO {

    @Getter
    @Setter
    public static class Response{
        private Long id;
        private Game game;
        private Player player;
        private List<String> currentText;
        private String playerResult;

        /* Entity -> DTO Response */
        public Response(LiveCast liveCast) {
            this.id = liveCast.getId();
            this.game = liveCast.getGame();
            this.player = liveCast.getPlayer();
            this.currentText = liveCast.getCurrentText();
            this.playerResult = liveCast.getPlayerResult();
        }

        public LiveCast toEntity(){
            return LiveCast.builder()
                .id(id)
                .game(game)
                .player(player)
                .currentText(currentText)
                .playerResult(playerResult)
                .build();
        }
    }
}
