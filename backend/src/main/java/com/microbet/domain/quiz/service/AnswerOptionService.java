package com.microbet.domain.quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microbet.domain.quiz.domain.AnswerOption;
import com.microbet.domain.quiz.domain.Question;
import com.microbet.domain.quiz.repository.AnswerOptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerOptionService {
    private final AnswerOptionRepository answerOptionRepository;

    @Transactional
    public Long saveAnswerOption(AnswerOption answerOption) {
        return answerOptionRepository.save(answerOption);
    }
}
