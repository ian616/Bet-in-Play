package com.microbet.domain.game.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.microbet.domain.game.domain.Game;
import com.microbet.domain.game.domain.LiveCast;
import com.microbet.domain.game.domain.ScoreBoard;
import com.microbet.domain.game.domain.Team;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LiveCastRepository {
    private final EntityManager em;

    public Long save(LiveCast liveCast) {
        em.persist(liveCast);
        return liveCast.getId();
    }

    public List<LiveCast> findAll() {
        return em.createQuery("select m from LiveCast m", LiveCast.class)
                .getResultList();
    }

    public LiveCast findById(Long id) {
        return em.createQuery("select m from LiveCast m where m.id = :id", LiveCast.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
