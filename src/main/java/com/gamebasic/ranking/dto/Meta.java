package com.gamebasic.ranking.dto;

import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class Meta {
    private Season season;
    private OffsetDateTime generatedAt;
    private int schemaVersion;
    private int totalRecords;

    @Getter
    public static class Season{
        private String id;
        private String name;
        private OffsetDateTime startsAt;
        private OffsetDateTime endsAt;
    }
}
