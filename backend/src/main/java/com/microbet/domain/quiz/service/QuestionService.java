package com.microbet.domain.quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microbet.domain.quiz.domain.Question;
import com.microbet.domain.quiz.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {
    
    private final QuestionRepository questionRepository;

    public Long saveQuestion(Question question) {
        return questionRepository.save(question);
    }
}
