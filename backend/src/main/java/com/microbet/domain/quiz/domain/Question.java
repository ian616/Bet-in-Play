package com.microbet.domain.quiz.domain;

import java.util.List;

import com.microbet.domain.game.domain.LiveCast;
import com.microbet.domain.game.enums.GameState;

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
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    private String content;

    //현재 질문이 만들어질 때의 중계상황 저장
    @ManyToOne
    @JoinColumn(name = "live_cast_id")
    private LiveCast liveCast;
    
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnswerOption> answerOptions; // 질문에 대한 답변 보기들
}
