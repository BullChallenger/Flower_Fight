package com.example.flower_fight.dto;

import com.example.flower_fight.domain.Card;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.io.Serializable;
import java.util.List;

public class DeckDTO implements Serializable {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class Request {

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Response {
        private List<Card> shuffledDeck;
    }
}
