package com.example.flower_fight.repository;

import com.example.flower_fight.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account>findByEmail(String email);
}
