package com.microbet.domain.game.domain;

import java.util.List;

import com.microbet.domain.game.embeddable.Player;

import jakarta.persistence.Column;
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

    private List<String> currentText;
}