package com.example.flower_fight.dto;

import com.example.flower_fight.domain.Game;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public class HouseDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Request {
        private String title;

        private BigDecimal minLimitAsset;

        private int maxLimitPlayer;

        @Builder
        public Request(String title, BigDecimal minLimitAsset, int maxLimitPlayer) {
            this.title = title;
            this.minLimitAsset = minLimitAsset;
            this.maxLimitPlayer = maxLimitPlayer;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateRequest {
        private Long houseId;

        private String title;

        private BigDecimal minLimitAsset;

        private int maxLimitPlayer;

        @Builder
        public UpdateRequest(Long houseId, String title, BigDecimal minLimitAsset, int maxLimitPlayer) {
            this.houseId = houseId;
            this.title = title;
            this.minLimitAsset = minLimitAsset;
            this.maxLimitPlayer = maxLimitPlayer;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Response {
        private Long houseId;

        private String title;

        private BigDecimal minLimitAsset;

        private int maxLimitPlayer;

        @Builder
        public Response(Long houseId, String title, BigDecimal minLimitAsset, int maxLimitPlayer) {
            this.houseId = houseId;
            this.title = title;
            this.minLimitAsset = minLimitAsset;
            this.maxLimitPlayer = maxLimitPlayer;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class EnterResponse {
        private Long houseId;

        private List<Game> gameList;

        @Builder
        public EnterResponse(Long houseId, List<Game> gameList) {
            this.houseId = houseId;
            this.gameList = gameList;
        }
    }
}
