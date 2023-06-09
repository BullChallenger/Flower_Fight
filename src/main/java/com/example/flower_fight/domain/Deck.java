package com.example.flower_fight.domain;

import com.example.flower_fight.dto.DeckDTO.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private final static int SIZE = 20;

    public List<Card> init() {
        List<Card> initDeck = new ArrayList<>();

        for (int i = 0; i < SIZE / 2; i++) {
            Card theCard = createCard(i + 1, false, false);
            initDeck.add(theCard);
        }
        for (int i = 0; i < SIZE / 2; i++) {
            Card theCard = createCard(i + 1, false, false);
            setHikari(theCard);
            initDeck.add(theCard);
        }

        return initDeck;
    }

    public Response shuffle(List<Card> initDeck) {
        Collections.shuffle(initDeck);

        return Response.builder()
                .shuffledDeck(initDeck)
                .build();
    }

    private Card createCard(int month, boolean isHikari, boolean isAnimal) {
        Card theCard = Card.builder()
                .month(month)
                .isHikari(isHikari)
                .isAnimal(isAnimal)
                .build();

        return theCard;
    }

    private void setHikari(Card theCard) {
        if (theCard.getMonth() == 1 || theCard.getMonth() == 3 || theCard.getMonth() == 8) {
            theCard.setHikari(true);
        }
    }

    private void setAnimal(Card theCard) {
        if (theCard.getMonth() == 4 || theCard.getMonth() == 7 || theCard.getMonth() == 9) {
            theCard.setAnimal(true);
        }
    }
}
