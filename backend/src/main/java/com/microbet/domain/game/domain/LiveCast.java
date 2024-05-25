package com.microbet.domain.game.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.microbet.domain.game.embeddable.Player;

import jakarta.annotation.Nullable;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveCast {

    @Id
    @GeneratedValue
    @Column(name = "live_cast_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Embedded
    private Player player;

    @ElementCollection
    @CollectionTable(name = "live_cast_current_text", joinColumns = @JoinColumn(name = "live_cast_id"))
    private List<String> currentText;

    @Nullable
    private String playerResult;

    private LocalDateTime lastUpdated;

    //===생성 메서드===//
    public static LiveCast createLiveCast(Player player, List<String> currentText, LocalDateTime lastUpdated) {

        return LiveCast.builder()
                .currentText(currentText)
                .player(player)
                .playerResult(generatePlayerResult(currentText, player))
                .lastUpdated(lastUpdated)
                .build();
    }

    public static String generatePlayerResult(List<String> currentText, Player player){
        String patternString = String.format("%s : ([^:]+)", player.getName());

        Pattern pattern = Pattern.compile(patternString);

        String returnString = null;
        
        for(String text: currentText){
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                returnString = matcher.group(1).trim();
            }
        }

        return returnString;
    }
}
