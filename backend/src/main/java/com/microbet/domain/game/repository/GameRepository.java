package com.microbet.domain.game.repository;

import org.springframework.stereotype.Repository;

import com.microbet.domain.game.domain.Game;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GameRepository {
    private final EntityManager em;

    public void save(Game game) {
        em.persist(game);
    }
}
