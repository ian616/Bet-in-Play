package com.microbet.domain.quiz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.gargoylesoftware.htmlunit.javascript.host.html.Option;
import com.microbet.domain.game.domain.Game;
import com.microbet.domain.quiz.domain.Question;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QuestionRepository {
    private final EntityManager em;

    public Long save(Question question) {
        em.persist(question);
        return question.getId();
    }

    public Optional<Question> findById(Long id) {
        try {
            Question question = em.createQuery("select m from Question m where m.id = :id", Question.class)
                    .setParameter("id", id)
                    .getSingleResult();

            return Optional.of(question);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
