package com.gamebasic.ranking.entity;

public class Entry {
    private int rank;
    private String playerName;
    private int clearTimeSeconds;
    private int remainingHp;
    private int bossTurns;
    private int deckSize;

    public Entry(
            int rank,
            String playerName,
            int clearTimeSeconds,
            int remainingHp,
            int bossTurns,
            int deckSize
    ) {
        this.rank = rank;
        this.playerName = playerName;
        this.clearTimeSeconds = clearTimeSeconds;
        this.remainingHp = remainingHp;
        this.bossTurns = bossTurns;
        this.deckSize = deckSize;
    }
}
