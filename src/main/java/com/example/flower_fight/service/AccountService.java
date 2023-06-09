package com.example.flower_fight.service;

import com.example.flower_fight.domain.Account;
import com.example.flower_fight.domain.Asset;
import com.example.flower_fight.dto.AccountDTO.*;
import com.example.flower_fight.exception.BaseException;
import com.example.flower_fight.exception.ResultType;
import com.example.flower_fight.repository.AccountCacheRepository;
import com.example.flower_fight.repository.AccountRepository;
import com.example.flower_fight.repository.AssetRepository;
import com.example.flower_fight.utils.AccountCacheKey;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final ModelMapper modelMapper;

    private final AccountRepository accountRepository;
    private final AssetRepository assetRepository;
    private final AccountCacheRepository accountCacheRepository;
    private final AccountCacheService accountCacheService;

    public CreateResponse create(CreateRequest request) {
        Account theAccount = accountRepository.save(modelMapper.map(request, Account.class));
        Asset theAsset = Asset.builder()
                .accountId(theAccount.getAccountId())
                .asset(request.getAsset())
                .build();

        assetRepository.save(theAsset);
        return modelMapper.map(theAccount, CreateResponse.class);
    }

    public GetResponse login(LoginRequest request) {
        Account theAccount = accountRepository.findByEmail(request.getEmail()).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        if (!theAccount.getPassword().equals(request.getPassword())) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        accountCacheRepository.setAccount(theAccount);
        return modelMapper.map(theAccount, GetResponse.class);
    }

    public GetResponse read(Long accountId) {
        Account theAccount = accountCacheService.loadAccountByAccountId(accountId);
        return modelMapper.map(theAccount, GetResponse.class);
    }

    public GetResponse update(UpdateRequest request) {
        Account theAccount = accountCacheService.loadAccountByAccountId(request.getAccountId());

        theAccount.setEmail(request.getEmail());
        theAccount.setNickName(request.getNickName());
        theAccount.setPassword(request.getPassword());
        theAccount.setName(request.getName());
        theAccount.setAge(request.getAge());

        accountCacheRepository.setAccount(theAccount);

        return modelMapper.map(accountRepository.save(theAccount), GetResponse.class);
    }

    public void delete(DeleteRequest request) {
        Account theAccount = accountCacheService.loadAccountByAccountId(request.getAccountId());

        theAccount.setIsDeleted(true);
        accountCacheRepository.setAccount(theAccount);
        accountRepository.save(theAccount);
    }
}
