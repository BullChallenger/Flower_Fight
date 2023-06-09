package com.example.flower_fight.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted=false")
public class Game extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long gameId;

    @Column
    private String title;

    @Column
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Long> playerIdList = new ArrayList<>();

    @Comment(value = "매 라운드 당 지불해야하는 베팅금")
    @Column(columnDefinition = "DECIMAL(15, 2)")
    private BigDecimal defaultBet;

    @Comment(value = "해당 게임에 걸려있는 금액")
    @Column(columnDefinition = "DECIMAL(17, 2)")
    private BigDecimal totalBetInGame;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "house_id")
    private House house;

    @Builder
    public Game(Long gameId, List<Long> playerIdList, BigDecimal defaultBet, BigDecimal totalBetInGame) {
        this.gameId = gameId;
        this.playerIdList = playerIdList;
        this.defaultBet = defaultBet;
        this.totalBetInGame = totalBetInGame;
    }
}