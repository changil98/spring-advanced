package com.gamebasic.ranking.controller;

import com.gamebasic.ranking.dto.RankingResponse;
import com.gamebasic.ranking.entity.RankingClient;
import com.gamebasic.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;
    private final RankingClient rankingClient; // 임시로 직접 주입

    @GetMapping("/rankings")
    public ResponseEntity<RankingResponse> getRankings() {
        return ResponseEntity.ok(rankingService.getRankings());
    }

}
