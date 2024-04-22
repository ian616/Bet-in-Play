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

    // ex) LOTTE
    @Enumerated(EnumType.STRING)
    private TeamName name;

    // ex) 롯데
    private String alias;

    // ex) 롯데 자이언츠
    private String fullName;

    private String logoString;
}
