package com.microbet.domain.game.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.microbet.domain.game.domain.Game;
import com.microbet.domain.game.domain.Team;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GameRepository {
    private final EntityManager em;

    public void save(Game game) {
        em.persist(game);
    }

    public List<Game> findAll() {
        return em.createQuery("select m from Game m", Game.class)
                .getResultList();
    }
}
