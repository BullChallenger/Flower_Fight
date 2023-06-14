package com.example.flower_fight.service;

import com.example.flower_fight.domain.House;
import com.example.flower_fight.dto.AccountDTO.AccountCacheDTO;
import com.example.flower_fight.dto.HouseDTO.*;
import com.example.flower_fight.exception.BaseException;
import com.example.flower_fight.exception.ResultType;
import com.example.flower_fight.repository.HouseRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class HouseService {

    private final ModelMapper modelMapper;

    private final HouseRepository houseRepository;

    public Response create(Request request) {
        House theHouse = houseRepository.save(modelMapper.map(request, House.class));
        return modelMapper.map(theHouse, Response.class);
    }

    public Response read(Long houseId) {
        House theHouse = houseRepository.findById(houseId).orElseThrow(() ->
                new BaseException(ResultType.SYSTEM_ERROR)
        );
        return modelMapper.map(theHouse, Response.class);
    }

    public Response update(UpdateRequest request) {
        House theHouse = houseRepository.findById(request.getHouseId()).orElseThrow(() ->
                new BaseException(ResultType.SYSTEM_ERROR)
        );
        theHouse.setTitle(request.getTitle());
        theHouse.setMinLimitAsset(request.getMinLimitAsset());
        theHouse.setMaxLimitPlayer(request.getMaxLimitPlayer());

        return modelMapper.map(houseRepository.save(theHouse), Response.class);
    }

    public void delete(Long houseId) {
        House theHouse = houseRepository.findById(houseId).orElseThrow(() ->
                new BaseException(ResultType.SYSTEM_ERROR)
        );
        theHouse.setIsDeleted(true);
        theHouse.getGameList().stream().forEach(game -> {
            game.setIsDeleted(true);
        });
        houseRepository.save(theHouse);
    }

    public EnterResponse enter(Long houseId, Authentication authentication) {
        House theHouse = houseRepository.findById(houseId).orElseThrow(() ->
                new BaseException(ResultType.SYSTEM_ERROR)
        );
        AccountCacheDTO theAccount = modelMapper.map(authentication.getPrincipal(), AccountCacheDTO.class);

        if (theHouse.getPlayerEmailList().contains(theAccount.getEmail())) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        theHouse.getPlayerEmailList().add(theAccount.getEmail());

        // TODO: Spring Security 써서 Validation 하기
        return modelMapper.map(theHouse, EnterResponse.class);
    }
}
