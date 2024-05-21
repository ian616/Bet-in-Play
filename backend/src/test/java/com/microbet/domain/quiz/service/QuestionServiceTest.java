package com.microbet.domain.quiz.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.microbet.domain.quiz.domain.AnswerOption;
import com.microbet.domain.quiz.domain.Question;

@SpringBootTest
@Transactional
public class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerOptionService answerOptionService;

    @Test
    void testSaveQuestion() {
        //given
        // Question question = new Question("다음 타석의 결과는?");
        // List<String> answerOptions = List.of("안타", "2루타", "삼진", "땅볼");

        // questionService.saveQuestion(question);
        // answerOptions.stream().forEach(answer->{
        //     answerOptionService.saveAnswerOption(AnswerOption.createAnswerOption(answer, question));
        // });
    }
}
