package com.microbet.domain.game.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.microbet.domain.game.domain.LiveCast;
import com.microbet.domain.game.embeddable.Player;
import com.microbet.domain.game.repository.LiveCastRepository;
import com.microbet.domain.quiz.service.AnswerOptionService;
import com.microbet.domain.quiz.service.QuestionService;

@SpringBootTest
@Transactional
public class LiveCastServiceTest {

    @Autowired
    private LiveCastService liveCastService;
    @Autowired
    private LiveCastRepository liveCastRepository;

    @Test
    void 중복선수_중계_테스트() {
        Player player = Player.builder()
                .playerId(1)
                .name("PlayerName")
                .battingOrder(1)
                .backNumber(10)
                .playerImageURL("player_image_url")
                .build();

        // 다른 currentText를 가진 LiveCast 엔티티 생성
        LiveCast liveCast1 = LiveCast.builder()
                .player(player)
                .currentText(Arrays.asList("Text1", "Text2")) // 다른 내용
                .build();

        // 다른 currentText를 가진 LiveCast 엔티티 생성
        LiveCast liveCast2 = LiveCast.builder()
                .player(player)
                .currentText(Arrays.asList("Text1", "Text2", "Text3")) // 다른 내용
                .build();

        // 저장
        liveCastService.saveLiveCast(liveCast1);
        liveCastService.saveLiveCast(liveCast2);
        liveCastService.findLiveCasts().stream().forEach(liveCast -> {
            System.out.println("ya!!!");
            System.out.println(liveCast.getCurrentText());
        });

        // // 테스트 - 동일한 player에 대한 LiveCast가 있는지 확인하고, 내용이 다른지 확인
        // Optional<LiveCast> retrievedLiveCastOpt =
        // liveCastRepository.findByPlayer(player);
        // assertTrue(retrievedLiveCastOpt.isPresent());

        // LiveCast retrievedLiveCast = retrievedLiveCastOpt.get();
        // assertNotEquals(liveCast1.getCurrentText(),
        // retrievedLiveCast.getCurrentText());
        // assertEquals(liveCast2.getCurrentText(), retrievedLiveCast.getCurrentText());
    }
}
