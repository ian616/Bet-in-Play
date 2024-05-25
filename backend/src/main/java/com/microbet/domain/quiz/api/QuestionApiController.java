package com.microbet.domain.quiz.api;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microbet.domain.quiz.domain.Question;
import com.microbet.domain.quiz.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class QuestionApiController {

    private final QuestionService questionService;

    @GetMapping("/api/v1/question")
    public Question getGeneratedQuestion() {
        questionService.generateQuestion();
        return questionService.findQuestion(1L).orElseGet(()->null);
    }

}
