package com.example.flower_fight.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Player {

    private Long accountId;

    private Long gameId;

    private String name;

    private List<Card> hand;

    private Set<String> combination;

    private String selectedCombination;

    private BigDecimal asset;

    private BetType betType;

    private boolean isDie;

    @Builder
    public Player(Long accountId, Long gameId, String name, List<Card> hand, Set<String> combination, String selectedCombination,
                  BigDecimal asset, BetType betType) {
        this.accountId = accountId;
        this.gameId = gameId;
        this.name = name;
        this.hand = hand;
        this.combination = combination;
        this.selectedCombination = selectedCombination;
        this.asset = asset;
        this.betType = betType;
    }
}
