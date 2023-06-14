package com.example.flower_fight.controller;

import com.example.flower_fight.dto.HouseDTO.*;
import com.example.flower_fight.dto.ResponseDTO;
import com.example.flower_fight.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.example.flower_fight.dto.ResponseDTO.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/houses")
public class HouseController {

    private final HouseService houseService;

    @PostMapping(value = "/create")
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(houseService.create(request));
    }

    @GetMapping(value = "/{houseId}/read")
    public ResponseDTO<Response> read(@PathVariable(value = "houseId") Long houseId) {
        return ok(houseService.read(houseId));
    }

    @PutMapping(value = "/update")
    public ResponseDTO<Response> update(@RequestBody UpdateRequest request) {
        return ok(houseService.update(request));
    }

    @DeleteMapping(value = "/{houseId}/delete")
    public ResponseDTO<Void> delete(@PathVariable(value = "houseId") Long houseId) {
        houseService.delete(houseId);
        return ok();
    }

    @PostMapping(value = "/{houseId}/enter")
    public ResponseDTO<EnterResponse> enter(@PathVariable(value = "houseId") Long houseId, Authentication authentication) {
        return ok(houseService.enter(houseId, authentication));
    }
}
