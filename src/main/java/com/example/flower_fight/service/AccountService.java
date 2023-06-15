package com.example.flower_fight.service;

import com.example.flower_fight.domain.Account;
import com.example.flower_fight.domain.Asset;
import com.example.flower_fight.dto.AccountDTO.*;
import com.example.flower_fight.exception.BaseException;
import com.example.flower_fight.exception.ResultType;
import com.example.flower_fight.repository.AccountCacheRepository;
import com.example.flower_fight.repository.AccountRepository;
import com.example.flower_fight.repository.AssetRepository;
import com.example.flower_fight.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final ModelMapper modelMapper;

    private final AccountRepository accountRepository;
    private final AssetRepository assetRepository;
    private final AccountCacheRepository accountCacheRepository;
    private final AccountCacheService accountCacheService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTime;

    public CreateResponse create(CreateRequest request) {
        Account theAccount = accountRepository.save(modelMapper.map(request, Account.class));
        Asset theAsset = Asset.builder()
                .accountId(theAccount.getAccountId())
                .asset(request.getAsset())
                .build();

        assetRepository.save(theAsset);
        return modelMapper.map(theAccount, CreateResponse.class);
    }

    public LoginResponse login(LoginRequest request) {
        Account theAccount = accountRepository.findByEmail(request.getEmail()).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        if (!theAccount.getPassword().equals(request.getPassword())) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        accountCacheRepository.setAccount(theAccount);
        String token = JwtUtils.generateToke(theAccount.getEmail(), theAccount.getNickName(), secretKey, expiredTime);
        LoginResponse response = modelMapper.map(theAccount, LoginResponse.class);

        response.setAsset(accountCacheRepository.getAccount(request.getEmail()).get().getAsset());
        response.setToken(token);

        return response;
    }

    public GetResponse read(Long accountId, Authentication authentication) {
        Account theAccount = modelMapper.map(authentication.getPrincipal(), Account.class);
        return modelMapper.map(theAccount, GetResponse.class);
    }

    public GetResponse update(UpdateRequest request, Authentication authentication) {
        Account theAccount = modelMapper.map(authentication.getPrincipal(), Account.class);

        theAccount.setEmail(request.getEmail());
        theAccount.setNickName(request.getNickName());
        theAccount.setPassword(request.getPassword());
        theAccount.setName(request.getName());
        theAccount.setAge(request.getAge());

        accountCacheRepository.setAccount(theAccount);

        return modelMapper.map(accountRepository.save(theAccount), GetResponse.class);
    }

    public void delete(DeleteRequest request, Authentication authentication) {
        Account theAccount = modelMapper.map(authentication.getPrincipal(), Account.class);

        theAccount.setIsDeleted(true);
        accountCacheRepository.setAccount(theAccount);
        accountRepository.save(theAccount);
    }
}
