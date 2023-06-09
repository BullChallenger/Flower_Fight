package com.example.flower_fight.controller;

import com.example.flower_fight.dto.AccountDTO.*;
import com.example.flower_fight.dto.ResponseDTO;
import com.example.flower_fight.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/accounts")
public class AccountController extends AbstractController {

    private final AccountService accountService;

    @PostMapping(value = "/create")
    public ResponseDTO<CreateResponse> create(@RequestBody CreateRequest request) {
        return ok(accountService.create(request));
    }

    @PostMapping(value = "/login")
    public ResponseDTO<GetResponse> login(@RequestBody LoginRequest request) {
        return ok(accountService.login(request));
    }

    @GetMapping(value = "/{accountId}/read")
    public ResponseDTO<GetResponse> read(@PathVariable(value = "accountId") Long accountId) {
        return ok(accountService.read(accountId));
    }

    @PutMapping(value = "/update")
    public ResponseDTO<GetResponse> update(@RequestBody UpdateRequest request) {
        return ok(accountService.update(request));
    }

    @DeleteMapping(value = "/delete")
    public ResponseDTO<Void> delete(@RequestBody DeleteRequest request) {
        accountService.delete(request);
        return ok();
    }
}
