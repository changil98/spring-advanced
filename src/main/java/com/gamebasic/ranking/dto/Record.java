package com.gamebasic.ranking.dto;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
public class Record {
    private Long id;
    private OffsetDateTime submittedAt;
    private Client client;
    private Player player;
    private Run run;
    private BossFight bossFight;
    private Deck deck;

    @Getter
    public static class Client {
        private String version;
        private String platform;
        private String locale;
    }

    @Getter
    public static class Player {
        private String id;
        private String name;
        private String region;
        private List<String> tags;
    }

    @Getter
    public static class Run {
        private String seed;
        private String status;
        private int clearedFloor;
        private int durationSeconds;
        private int finalHp;
        private List<Floor> floors;
    }

    @Getter
    public static class Floor {
        private int floor;
        private String enemy;
        private int turns;
        private int hpAfter;
        private List<Reward> rewards;
    }

    @Getter
    public static class Reward {
        private List<String> offered;
        private String picked;
    }

    @Getter
    public static class BossFight {
        private List<Phase> phases;
        private String finishingCard;
        private int totalTurns;
    }

    @Getter
    public static class Phase {
        private String phase;
        private int turns;
        private int damageTaken;
    }

    @Getter
    public static class Deck {
        private int size;
        private List<Card> cards;
    }

    @Getter
    public static class Card {
        private String cardType;
        private int acquiredFloor;
    }
}
