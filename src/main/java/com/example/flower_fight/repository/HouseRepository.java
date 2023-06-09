package com.example.flower_fight.repository;

import com.example.flower_fight.domain.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
}
