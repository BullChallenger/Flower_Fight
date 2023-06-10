package com.example.flower_fight.domain.constant;

public enum BetType {
    DIE,
    CALL, // 앞사람이 베팅한 만큼 -> 리레이즈 불가
    HALF, // 깔린 돈의 50%
    QUARTER, // 깔린 돈의 25%
    DDADANG, // 앞 사람이 건 금액의 2배
    CHECK, // 돈 거는거 없이 순서 넘김 -> 리레이즈 불가, 선을 잡은 사람만 가능
    BBING, // 참가비와 동일한 금액 베팅
    FULL, // 깔린 돈만큼 베팅
    ALL_IN // 전재산 박기
    ;
}
