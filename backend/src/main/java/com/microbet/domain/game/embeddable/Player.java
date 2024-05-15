package com.microbet.domain.game.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Builder 
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private String name;
    private int battingOrder;
    private int backNumber;
    private String playerImageURL;
}
