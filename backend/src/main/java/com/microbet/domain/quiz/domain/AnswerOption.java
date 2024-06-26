package com.microbet.domain.quiz.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.microbet.domain.game.enums.GameState;
import com.microbet.domain.quiz.enums.AnswerStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerOption {

    @Id
    @GeneratedValue
    @Column(name = "answer_option_id")
    private Long id;

    private String content;

    private boolean answerStatus;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question; // 해당 답변 보기의 질문

    //=== 연관관계 메서드===//
    public void setQuestion(Question question){
        this.question = question;
        question.getAnswerOptions().add(this);
    }

    // ===생성 메서드===//
    public static AnswerOption createAnswerOption(String content, Question question) {
        
        AnswerOption answerOption = AnswerOption.builder()
                .content(content)
                .answerStatus(false)
                .question(question)
                .build();

        answerOption.setQuestion(question);

        return answerOption;
    }
}
