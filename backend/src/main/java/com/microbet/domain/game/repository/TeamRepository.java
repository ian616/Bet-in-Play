package com.microbet.domain.game.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.microbet.domain.game.domain.Team;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeamRepository {
    private final EntityManager em;

    public Long save(Team team) {
        em.persist(team);
        return team.getId();
    }

    public List<Team> findAll() {
        return em.createQuery("select m from Team m", Team.class)
                .getResultList();
    }

    public Team findByAlias(String alias) {
        return em.createQuery("select m from Team m where m.alias = :alias", Team.class)
                .setParameter("alias", alias)
                .getSingleResult();
    }
}


