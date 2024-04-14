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

    public void save(Team team) {
        em.persist(team);
    }

    public List<Team> findAll() {
        return em.createQuery("select m from Team m", Team.class)
                .getResultList();
    }
}


