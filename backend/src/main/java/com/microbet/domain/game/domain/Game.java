package com.microbet.domain.game.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Builder
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
    private String location;

}
