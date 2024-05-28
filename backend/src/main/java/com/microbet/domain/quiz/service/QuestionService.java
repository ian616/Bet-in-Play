package com.microbet.domain.quiz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microbet.domain.game.domain.LiveCast;
import com.microbet.domain.game.repository.LiveCastRepository;
import com.microbet.domain.game.service.LiveCastService;
import com.microbet.domain.quiz.domain.AnswerOption;
import com.microbet.domain.quiz.domain.Question;
import com.microbet.domain.quiz.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final LiveCastRepository liveCastRepository;

    private final QuestionRepository questionRepository;
    private final AnswerOptionService answerOptionService;

    public Optional<Question> findQuestion(Long id) {
        return questionRepository.findById(id);
    }

    @Transactional
    public Long saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Transactional
    public void generateQuestion() {
        Question question = Question.builder()
                .content("다음 타석의 결과는?")
                .isConfirmed(false)
                .build();

        List<String> answerOptions = List.of("안타", "2루타", "3루타", "홈런", "삼진", "볼넷", "땅볼", "플라이", "기타");

        saveQuestion(question);
        answerOptions.stream().forEach(answer -> {
            answerOptionService.saveAnswerOption(AnswerOption.createAnswerOption(answer, question));
        });
    }

    public void checkAnswer(Long questionId) {
        LiveCast liveCast = liveCastRepository.findById(2L).orElseGet(()->null);
        Question question = findQuestion(questionId).orElseGet(()->null);

        if ((question != null) && (!question.isConfirmed())) {
            if(liveCast.getPlayerResult() != null){
                question.confirmAnswer(evaluateResultQ1(liveCast.getPlayerResult()));
            }else{
                
            }
        }
    }

    private Long evaluateResultQ1(String playerResult){
        //TODO: string에서 options으로 변환하는 알고리즘 필요함
        
        if(playerResult.equals("볼넷")){
            return 6L;
        }
        return 9L;
    }
}
