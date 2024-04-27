package com.microbet.domain.game.domain;

import com.microbet.domain.game.enums.GameState;

import jakarta.persistence.Column;
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
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id @GeneratedValue
    @Column(name = "game_id")
    private Long id;

    //===어웨이팀 정보===//
    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    //===홈팀 정보===//
    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    //===게임 정보===//
    private Long daumGameId;

    @Enumerated(EnumType.STRING)
    private GameState gameState;

    private String startTime;

    private String location;

    private String broadcasting;

    //===전광판 정보===//
    @OneToOne(mappedBy = "game")
    private ScoreBoard scoreBoard;
}
