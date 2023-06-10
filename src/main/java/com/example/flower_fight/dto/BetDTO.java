package com.example.flower_fight.dto;

import com.example.flower_fight.domain.constant.BetType;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

public class BetDTO implements Serializable {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private BigDecimal asset;

        private BetType betType;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private BigDecimal asset;

        private BigDecimal betting;
    }
}
