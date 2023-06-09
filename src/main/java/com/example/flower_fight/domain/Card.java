package com.example.flower_fight.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Card {

    private final int month;

    private boolean isHikari;

    private boolean isAnimal;

    @Builder
    public Card(int month, boolean isHikari, boolean isAnimal) {
        this.month = month;
        this.isHikari = isHikari;
        this.isAnimal = isAnimal;
    }

    public void setHikari(boolean isHikari) {
        this.isHikari = isHikari;
    }

    public void setAnimal(boolean isAnimal) { this.isAnimal = isAnimal; }
}
