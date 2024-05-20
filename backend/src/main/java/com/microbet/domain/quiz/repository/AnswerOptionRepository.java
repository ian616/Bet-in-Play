package com.microbet.domain.quiz.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.microbet.domain.quiz.domain.AnswerOption;
import com.microbet.domain.quiz.domain.Question;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AnswerOptionRepository {
    private final EntityManager em;

    public Long save(AnswerOption answerOption) {
        em.persist(answerOption);
        return answerOption.getId();
    }
}
