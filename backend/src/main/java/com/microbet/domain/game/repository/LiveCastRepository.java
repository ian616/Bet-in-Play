package com.microbet.domain.game.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.microbet.domain.game.domain.Game;
import com.microbet.domain.game.domain.LiveCast;
import com.microbet.domain.game.domain.ScoreBoard;
import com.microbet.domain.game.domain.Team;
import com.microbet.domain.game.embeddable.Player;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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

    public Optional<LiveCast> findById(Long id) {
        LiveCast liveCast = em.find(LiveCast.class, id);
        return Optional.ofNullable(liveCast);
    }

    public Optional<LiveCast> findByPlayer(Player player) {
        String jpql = "SELECT lc FROM LiveCast lc WHERE lc.player.playerId = :playerId";
        TypedQuery<LiveCast> query = em.createQuery(jpql, LiveCast.class);
        query.setParameter("playerId", player.getPlayerId());

        return query.getResultStream().findFirst();
    }

    public Optional<LiveCast> findByCurrentText(List<String> currentText) {
        String jpql = "SELECT lc FROM LiveCast lc JOIN lc.currentText ct WHERE ct IN :currentText GROUP BY lc HAVING COUNT(ct) = :textSize";
        TypedQuery<LiveCast> query = em.createQuery(jpql, LiveCast.class);
        query.setParameter("currentText", currentText);
        query.setParameter("textSize", (long) currentText.size());

        List<LiveCast> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public void deleteAllEntities() {
        em.createQuery("DELETE FROM LiveCast").executeUpdate();
    }
}
