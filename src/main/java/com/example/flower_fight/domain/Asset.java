package com.example.flower_fight.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted=false")
public class Asset extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_id", nullable = false, updatable = false)
    private Long assetId;

    @Comment(value = "사용자 ID")
    @Column(nullable = false, updatable = false)
    private Long accountId;

    @Comment(value = "자산")
    @Column(nullable = false, columnDefinition = "DECIMAL(15, 2)")
    private BigDecimal asset;

    @Builder
    public Asset(Long assetId, Long accountId, BigDecimal asset) {
        this.assetId = assetId;
        this.accountId = accountId;
        this.asset = asset;
    }
}
