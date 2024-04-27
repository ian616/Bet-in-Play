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

    public Long save(Game game) {
        em.persist(game);
        return game.getId();
    }

    public List<Game> findAll() {
        return em.createQuery("select m from Game m", Game.class)
                .getResultList();
    }

    public Game findById(Long id){
        return em.createQuery("select m from Game m where m.id = :id", Game.class)
        .setParameter("id", id)
        .getSingleResult();
    }

    public Game findByDaumGameId(Long daumGameId) {
        return em.createQuery("select m from Game m where m.daumGameId = :daumGameId", Game.class)
                .setParameter("daumGameId", daumGameId)
                .getSingleResult();
    }
}
