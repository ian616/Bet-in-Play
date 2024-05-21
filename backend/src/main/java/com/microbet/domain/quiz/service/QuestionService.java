package com.microbet.domain.quiz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microbet.domain.game.service.LiveCastService;
import com.microbet.domain.quiz.domain.AnswerOption;
import com.microbet.domain.quiz.domain.Question;
import com.microbet.domain.quiz.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {
    
    private final QuestionRepository questionRepository;
    private final AnswerOptionService answerOptionService;

    public Question findQuestion(){
        return questionRepository.findById(1L);
    }

    @Transactional
    public Long saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Transactional
    public void generateQuestion(){
        Question question = Question.builder()
                                    .content("다음 타석의 결과는?")
                                    .liveCast(null)
                                    .build();

        
        List<String> answerOptions = List.of("안타", "2루타", "삼진", "땅볼");

        saveQuestion(question);
        answerOptions.stream().forEach(answer->{
            answerOptionService.saveAnswerOption(AnswerOption.createAnswerOption(answer, question));
        });
    }

    public void checkAnswer(){
        // 
    }
}
