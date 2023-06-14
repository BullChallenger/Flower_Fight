package com.example.flower_fight.service;

import com.example.flower_fight.domain.*;
import com.example.flower_fight.dto.GameDTO.*;
import com.example.flower_fight.exception.BaseException;
import com.example.flower_fight.exception.ResultType;
import com.example.flower_fight.repository.AssetRepository;
import com.example.flower_fight.repository.GameRepository;
import com.example.flower_fight.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final ModelMapper modelMapper;

    private final HouseRepository houseRepository;
    private final GameRepository gameRepository;
    private final AssetRepository assetRepository;

    private final AccountCacheService accountCacheService;

    public GetResponse create(CreateRequest request, Authentication authentication) {
        if (gameRepository.findByTitle(request.getTitle()).isPresent()) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        Game theGame = modelMapper.map(request, Game.class);
        Player thePlayer = modelMapper.map(authentication.getPrincipal(), Player.class);

        House theHouse = houseRepository.findById(request.getHouseId()).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        theGame.setHouse(theHouse);
        theGame.getPlayerEmailList().add(thePlayer.getEmail());
        theGame.setDefaultBet(request.getDefaultBet());

        Game savedGame = gameRepository.save(theGame);

        return modelMapper.map(savedGame, GetResponse.class);
    }

    public GetResponse read(Long gameId) {
        Game theGame = gameRepository.findById(gameId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        return modelMapper.map(theGame, GetResponse.class);
    }

    public GetResponse update(UpdateRequest request) {
        Game theGame = gameRepository.findById(request.getHouseId()).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        theGame.setTitle(request.getTitle());
        theGame.setDefaultBet(request.getDefaultBet());

        return modelMapper.map(gameRepository.save(theGame), GetResponse.class);
    }

    public void delete(Long gameId) {
        Game theGame = gameRepository.findById(gameId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        theGame.setIsDeleted(true);
        gameRepository.save(theGame);
    }

    public EnterResponse enter(Long gameId, Authentication authentication) {
        Game theGame = gameRepository.findById(gameId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        Player thePlayer = modelMapper.map(authentication.getPrincipal(), Player.class);
        if (theGame.getPlayerEmailList().contains(thePlayer.getEmail())) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Asset theAsset = assetRepository.findByAccountId(thePlayer.getAccountId()).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        thePlayer.setAsset(theAsset.getAsset());
        theGame.getPlayerEmailList().add(thePlayer.getEmail());
        List<Player> thePlayers = theGame.getPlayerEmailList().stream().map(accountCacheService::loadAccountByEmail)
                .map(account -> modelMapper.map(account, Player.class)).collect(Collectors.toList());

        gameRepository.save(theGame);

        return EnterResponse.builder()
                .thePlayers(thePlayers)
                .build();
    }

    public ExitResponse exit(Long gameId, Authentication authentication) {
        Player thePlayer = modelMapper.map(authentication.getPrincipal(), Player.class);
        System.out.println(thePlayer);

        Game theGame = gameRepository.findById(gameId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        House theHouse = houseRepository.findById(theGame.getHouse().getHouseId()).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        Asset theAsset = assetRepository.findByAccountId(thePlayer.getAccountId()).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        theAsset.setAsset(thePlayer.getAsset());
        assetRepository.save(theAsset);

        theGame.getPlayerEmailList().remove(thePlayer.getEmail());
        gameRepository.save(theGame);

        return ExitResponse.builder()
                .thePlayer(thePlayer)
                .theGames(theHouse.getGameList())
                .build();
    }

    public Response process(int roundCount, Request request) {
        List<Player> players = request.getPlayers();
        List<Card> deck = request.getDeck();

        if (players.size() > 4) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        if (roundCount <= 3) {
            for (Player player : players) {
                player.getHand().add(deck.remove(0));
                List<Card> theHand = player.getHand();
                player.setCombination(checkHand(theHand));
            }
        } else {
            for (Player player : players) {
                List<Card> theHand = player.getHand();
                player.setCombination(checkHand(theHand));
            }
        }

        return Response.builder()
                .players(players)
                .deck(deck)
                .build();
    }

    private Set<String> checkHand(List<Card> hand) {
        int hikariCnt = 0;
        int[] duplicateNumCnt = new int[11];
        List<Card> hikariCards = new ArrayList<>();
        Set<String> combination = new HashSet<>();

        for (Card card : hand) {
            duplicateNumCnt[card.getMonth()] += 1;
            if (card.isHikari()) {
                hikariCnt += 1;
            }
        }

        // 광땡 찾기
        if (hikariCnt >= 2) {
            int hikari = 0;
            hikariCards.addAll(hand.stream().filter(Card::isHikari).collect(Collectors.toList()));
            for (Card card : hikariCards) {
                hikari += card.getMonth();
            }
            if (hikari == 4) {
                combination.add("일삼광땡");
            } else if (hikari == 9) {
                combination.add("일팔광땡");
            } else if (hikari == 11) {
                combination.add("삼팔광땡");
            } else {
                combination.add("일삼광땡");
                combination.add("일팔광땡");
                combination.add("삼팔광땡");
            }
        }

        // 땡 찾기
        if (Arrays.stream(duplicateNumCnt).max().getAsInt() == 2) {
            for (int i = 1; i < duplicateNumCnt.length; i++) {
                if (duplicateNumCnt[i] == 2) {
                    combination.add(i + "땡");
                }
            }
        }


        if (duplicateNumCnt[4] == 1) {
            if (duplicateNumCnt[7] == 1) {
                if (hand.stream().filter(card ->
                        (card.getMonth() == 4 && card.isAnimal()) ||
                        (card.getMonth() == 7 && card.isAnimal())).count() == 2) {
                    combination.add("암행어사");
                } else {
                    combination.add("1끗");
                }
            }

            if (duplicateNumCnt[9] == 1) {
                if (hand.stream().filter(card ->
                        (card.getMonth() == 4 && card.isAnimal()) ||
                                (card.getMonth() == 9 && card.isAnimal())).count() == 2) {
                    combination.add("멍텅구리구사");
                } else {
                    combination.add("사구파토");
                }
            }

            if (duplicateNumCnt[6] == 1) {
                combination.add("세륙");
            }

            if (duplicateNumCnt[10] == 1) {
                combination.add("장사");
            }
        }

        // 땡잡이
        if (duplicateNumCnt[3] == 1 && duplicateNumCnt[7] == 1) {
            if (hand.stream().filter(card ->
                    (card.getMonth() == 3 && card.isHikari()) ||
                    (card.getMonth() == 7 && card.isAnimal())).count() == 2) {
                combination.add("땡잡이");
            } else {
                combination.add("망통");
            }
        }

        // 알리, 독사, 구삥, 장삥
        if (duplicateNumCnt[1] == 1) {
            if (duplicateNumCnt[2] == 1) {
                combination.add("알리");
            }
            if (duplicateNumCnt[4] == 1) {
                combination.add("독사");
            }
            if (duplicateNumCnt[9] == 1) {
                combination.add("구삥");
            }
            if (duplicateNumCnt[10] == 1) {
                combination.add("장삥");
            }
        }

        for (int i = 1; i < duplicateNumCnt.length; i++) {
            for (int j = i + 1; j < duplicateNumCnt.length; j++) {
                if (duplicateNumCnt[i] >= 1 && duplicateNumCnt[j] >= 1) {
                    int oneStep = (i + j) % 10;
                    if (oneStep == 0) {
                        combination.add("망통");
                    } else {
                        combination.add(oneStep + "끗");
                    }
                }
            }
        }
        return combination;
    }
}
