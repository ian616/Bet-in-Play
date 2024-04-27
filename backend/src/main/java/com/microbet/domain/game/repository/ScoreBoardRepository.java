package com.microbet.domain.game.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.microbet.domain.game.domain.Game;
import com.microbet.domain.game.domain.ScoreBoard;
import com.microbet.domain.game.domain.Team;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ScoreBoardRepository {
    private final EntityManager em;

    public Long save(ScoreBoard scoreBoard) {
        em.persist(scoreBoard);
        return scoreBoard.getId();
    }

    public List<ScoreBoard> findAll() {
        return em.createQuery("select m from ScoreBoard m", ScoreBoard.class)
                .getResultList();
    }

    public ScoreBoard findById(Long id) {
        return em.createQuery("select m from ScoreBoard m where m.id = :id", ScoreBoard.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
