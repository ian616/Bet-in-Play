package com.microbet.domain.game.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Builder
public class ScoreBoard {

    @Id @GeneratedValue
    @Column(name = "scoreboard_id")
    private Long id;

    @OneToOne(mappedBy = "scoreBoard")
    private Game game;

    private List<Integer> awayTeamScores;
    private List<Integer> homeTeamScores;

    private int awayTeamScore;
    private int homeTeamScore;

    private int awayTeamHit;
    private int homeTeamHit;

    private int awayTeamError;
    private int homeTeamError;

    private int awayTeamBB;
    private int homeTeamBB;
}
