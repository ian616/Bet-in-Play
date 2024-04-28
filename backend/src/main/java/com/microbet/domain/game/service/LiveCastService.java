package com.microbet.domain.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microbet.domain.game.repository.GameRepository;
import com.microbet.domain.game.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LiveCastService {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void scrapScoreBoardInfo(Long id){
        
    }
}
