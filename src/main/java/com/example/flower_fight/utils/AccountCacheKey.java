package com.example.flower_fight.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountCacheKey {
    public static final Duration ACCOUNT_CACHE_TTL = Duration.ofDays(3);
    public static final String ACCOUNT = "ACCOUNT";
}
