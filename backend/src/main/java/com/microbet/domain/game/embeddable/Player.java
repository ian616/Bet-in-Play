package com.microbet.domain.game.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;

@Embeddable
@Builder 
@Getter
public class Player {
    private String name;
    private int battingOrder;
    private int backNumber;
    private String playerImageURL;
}
