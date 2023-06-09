package com.example.flower_fight.repository;

import com.example.flower_fight.domain.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findByAccountId(Long accountId);
}
