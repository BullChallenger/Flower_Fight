package com.example.flower_fight.repository;

import com.example.flower_fight.domain.Account;
import com.example.flower_fight.dto.AccountDTO.*;
import com.example.flower_fight.utils.AccountCacheKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AccountCacheRepository {

    private final RedisTemplate<String, AccountCacheDTO> accountRedisTemplate;
    private final ModelMapper modelMapper;

    public void setAccount(Account account) {
        String email = account.getEmail();
        AccountCacheDTO theAccount = modelMapper.map(account, AccountCacheDTO.class);

        String key = getKey(email);
        if (getAccount(email).isPresent()) {
            accountRedisTemplate.delete(key);
        }
        log.info("Set Account to Redis {}: {}", key, account);
        accountRedisTemplate.opsForValue().set(key, theAccount, AccountCacheKey.ACCOUNT_CACHE_TTL);
    }

    public Optional<AccountCacheDTO> getAccount(String email) {
        String key = getKey(email);
        AccountCacheDTO theAccount = accountRedisTemplate.opsForValue().get(key);
        log.info("Get data from Redis {}: {}", key, theAccount);
        return Optional.ofNullable(theAccount);
    }

    private String getKey(String email) { return "ACCOUNT: " + email; }
}
