package com.example.flower_fight.controller;

import com.example.flower_fight.domain.Card;
import com.example.flower_fight.domain.Deck;
import com.example.flower_fight.dto.DeckDTO;
import com.example.flower_fight.dto.GameDTO.*;
import com.example.flower_fight.dto.ResponseDTO;
import com.example.flower_fight.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/games")
public class GameController extends AbstractController {

    private final GameService gameService;

    @PostMapping(value = "/create")
    public ResponseDTO<GetResponse> create(@RequestBody CreateRequest request, Authentication authentication) {
        return ok(gameService.create(request, authentication));
    }

    @GetMapping(value = "/{gameId}/read")
    public ResponseDTO<GetResponse> create(@PathVariable(value = "gameId") Long gameId) {
        return ok(gameService.read(gameId));
    }

    @PutMapping(value = "/update")
    public ResponseDTO<GetResponse> update(@RequestBody UpdateRequest request) {
        return ok(gameService.update(request));
    }

    @DeleteMapping(value = "/{gameId}/delete")
    public ResponseDTO<Void> delete(@PathVariable(value = "gameId") Long gameId) {
        gameService.delete(gameId);
        return ok();
    }

    @PostMapping(value = "/{gameId}/enter")
    public ResponseDTO<EnterResponse> enter(@PathVariable(value = "gameId") Long gameId, Authentication authentication) {
        return ok(gameService.enter(gameId, authentication));
    }

    @PostMapping(value = "/{gameId}/exit")
    public ResponseDTO<ExitResponse> exit(@PathVariable(value = "gameId") Long gameId, Authentication authentication) {
        return ok(gameService.exit(gameId, authentication));
    }

    @GetMapping(value = "/start")
    public ResponseDTO<DeckDTO.Response> initGame() {
        Deck deck = new Deck();
        List<Card> initDeck = deck.init();

        return ok(deck.shuffle(initDeck));
    }

    @GetMapping(value = "/{roundCount}/round")
    public ResponseDTO<Response> divideHand(@PathVariable(value = "roundCount") int roundCount,
                                            @RequestBody Request request) {
        return ok(gameService.process(roundCount, request));
    }
}

