package com.microbet.domain.game.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.microbet.domain.game.domain.LiveCast;
import com.microbet.domain.game.embeddable.Player;

import jakarta.persistence.EntityManager;

@DataJpaTest
public class LiveCastRepositoryTest {
    @Autowired
    private EntityManager em;

    private LiveCastRepository liveCastRepository;

    @BeforeEach
    public void setUp() {
        liveCastRepository = new LiveCastRepository(em);

        Player player = new Player(1, "John Doe", 3, 10, "url");
        LiveCast liveCast = LiveCast.createLiveCast(player, Arrays.asList("Text 1"), LocalDateTime.now());

        em.persist(liveCast);
        em.flush();
        em.clear();
    }

    @Test
    public void testFindByPlayer_PlayerExists() {
        Player player = new Player(1, "John Doe", 3, 10, "url");
        Optional<LiveCast> liveCast = liveCastRepository.findByPlayer(player);

        assertTrue(liveCast.isPresent());
        System.out.println(liveCast.get());
    }

    @Test
    public void testFindByPlayer_PlayerDoesNotExist() {
        Player player = new Player(2, "Jane Doe", 4, 11, "url2");
        Optional<LiveCast> liveCast = liveCastRepository.findByPlayer(player);

        assertFalse(liveCast.isPresent());
    }
}
