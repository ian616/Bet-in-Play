package com.microbet.domain.game.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class ScoreBoard {

    @Id @GeneratedValue
    @Column(name = "scoreboard_id")
    private Long id;

    @OneToOne
    @JoinColumn(name="game_id")
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
