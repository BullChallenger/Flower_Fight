package com.example.flower_fight.service;

import com.example.flower_fight.domain.Account;
import com.example.flower_fight.exception.BaseException;
import com.example.flower_fight.exception.ResultType;
import com.example.flower_fight.repository.AccountCacheRepository;
import com.example.flower_fight.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCacheService {

    private final AccountRepository accountRepository;
    private final AccountCacheRepository accountCacheRepository;

    private final ModelMapper modelMapper;

    public Account loadAccountByAccountId(Long accountId) {
        return accountCacheRepository.getAccount(accountId).map(accountCacheDTO ->
                modelMapper.map(accountCacheDTO, Account.class)).orElseGet(() ->
                accountRepository.findById(accountId).orElseThrow(() ->
                        new BaseException(ResultType.SYSTEM_ERROR)
                )
        );
    }
}
