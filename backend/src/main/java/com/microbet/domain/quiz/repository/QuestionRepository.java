package com.microbet.domain.quiz.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.microbet.domain.game.domain.Game;
import com.microbet.domain.quiz.domain.Question;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QuestionRepository {
    private final EntityManager em;

    public Long save(Question question) {
        em.persist(question);
        return question.getId();
    }

    public Question findById(Long id){
        return em.createQuery("select m from Question m where m.id = :id", Question.class)
        .setParameter("id", id)
        .getSingleResult();
    }
}
