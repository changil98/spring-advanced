package com.gamebasic.ranking.service;

import com.gamebasic.ranking.dto.Entry;
import com.gamebasic.ranking.dto.RankingResponse;
import com.gamebasic.ranking.dto.RankingSource;
import com.gamebasic.ranking.dto.Record;
import com.gamebasic.ranking.entity.CardType;
import com.gamebasic.ranking.entity.RankingClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingClient rankingClient;

    public RankingResponse getRankings() {
        RankingSource rankingSource = rankingClient.fetch();
        int excludedCount = 0; // 치트 사용 계정 수 증가치
        int rank = 1; // 임시
        List<Entry> entries = new ArrayList<>();
        List<Record> records = new ArrayList<>();
        for (Record record : rankingSource.getRecords()) {
            Record.Run run = record.getRun();
            // 클리어 하지 않았다면 패스 여기는 치트 적용 x
            if (!"CLEARED".equals(run.getStatus())
                    || run.getClearedFloor() < 10) {
                continue;
            }
            // 치트 사용 여부 체크
            if (checkingCheating(record)) {
                excludedCount++;
                continue;
            }
            // 정상 기록들 추가
            records.add(record);
        }

        // 정렬
        records.sort(Comparator.comparing((Record r) -> r.getRun().getDurationSeconds())
                .thenComparing(r -> r.getRun().getFinalHp(), Comparator.reverseOrder())
                .thenComparing(Record::getId));

        // 기록은 플레이어 당 하나
        Map<String, Record> map = new LinkedHashMap<>();
        for (Record record : records) {
            map.putIfAbsent(record.getPlayer().getId(), record);
        }
        List<Record> deduplicationRecords = new ArrayList<>(map.values());

        for (Record record : deduplicationRecords) {
            Entry entry = new Entry(
                    rank,
                    record.getPlayer().getName(),
                    record.getRun().getDurationSeconds(),
                    record.getRun().getFinalHp(),
                    record.getBossFight().getTotalTurns(),
                    record.getDeck().getSize()
            );
            rank++;
            entries.add(entry);
        }

        RankingResponse rankingResponse = new RankingResponse(
                rankingSource.getMeta().getSeason().getId(),
                rankingSource.getMeta().getTotalRecords(),
                excludedCount,
                entries
        );
        return rankingResponse;// 아직 변환 전이니 임시로
    }

    // 치트 사용 여부 체크
    private boolean checkingCheating(Record record) {
        Record.Run run = record.getRun();
        Record.Deck deck = record.getDeck();
        Record.BossFight bossFight = record.getBossFight();

        // 1. 클리어 시간 300 미만이면 이상 기록
        if (run.getDurationSeconds() < 300) {
            return true;
        }
        // 2. 남은 HP가 1보다 작거나 99보다 크다면 이상 기록
        if (run.getFinalHp() < 1 || run.getFinalHp() > 99) {
            return true;
        }
        // 3. 덱 크기가 9장 미만 or 20장 초과면 이상 기록
        if ((deck.getSize() < 9 || deck.getSize() > 20)
                || deck.getSize() != deck.getCards().size()
        ) {
            return true;
        }
        // 덱 체크
        for (Record.Card card : deck.getCards()) {
            // 4. 보유한 카드들이 카드 타입 목록에 있는 값이 아니면 이상 기록
            if (Arrays.stream(CardType.values())
                    .noneMatch(c -> c.name().equals(card.getCardType()))) {
                return true;
            }
            // 5. 카드를 획득한 층이 0층 보다 작고 9층 보다 높으면 이상 기록
            if (card.getAcquiredFloor() < 0 || card.getAcquiredFloor() > 9) {
                return true;
            }
        }
        // 6-1. 보스 페이즈가 3개가 아니고 순서가 다르면 이상 기록
        List<String> comparePhase = List.of("THRONE", "UNBOUND", "ECLIPSE");
        List<String> phaseName = bossFight.getPhases().stream()
                .map(Record.Phase::getPhase)
                .toList();
        if (!phaseName.equals(comparePhase)) {
            return true;
        }
        // 총 턴수 체크
        int turns = 0;
        for (Record.Phase phase : bossFight.getPhases()) {
            // 6-2. 각 페이즈의 턴이 1보다 작으면 이상 기록
            if (phase.getTurns() < 1) {
                return true;
            }
            turns += phase.getTurns();
        }
        // 6-3. 총 턴수가 다르면 이상 기록
        if (bossFight.getTotalTurns() != turns) {
            return true;
        }
        // 7. 마지막 사용 카드가 가지고 있지 않은 카드면 이상 기록
        if (deck.getCards().stream()
                .noneMatch(c -> c.getCardType().equals(bossFight.getFinishingCard()))) {
            return true;
        }

        return false;
    }
}
