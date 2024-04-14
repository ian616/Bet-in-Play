package com.microbet.domain.game.domain;

import com.microbet.domain.game.enums.TeamName;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@Builder
@ToString
public class Team {

    @Id @GeneratedValue
    public Long id;

    @Enumerated(EnumType.STRING)
    private TeamName name;

    private String alias;
}
