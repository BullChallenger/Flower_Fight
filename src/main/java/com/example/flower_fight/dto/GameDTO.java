package com.example.flower_fight.dto;

import com.example.flower_fight.domain.Card;
import com.example.flower_fight.domain.Player;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class GameDTO implements Serializable {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private List<Player> players;

        private List<Card> deck;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CreateRequest {
        private Long houseId;

        private String title;

        private List<String> playerEmailList;

        private BigDecimal defaultBet;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateRequest {
        private Long houseId;

        private String title;

        private BigDecimal defaultBet;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private List<Player> players;

        private List<Card> deck;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GetResponse {
        private Long gameId;

        private String title;

        private BigDecimal defaultBet;

        private List<String> playerEmailList;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnterResponse {
        private List<Player> thePlayers;
    }
}
