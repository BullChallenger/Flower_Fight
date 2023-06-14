package com.example.flower_fight.domain;

import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted=false")
public class House extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id", nullable = false, updatable = false)
    private Long houseId;

    @Comment(value = "제목")
    @Column(nullable = false)
    private String title;

    @Comment(value = "입장 제한 자산")
    @Column(columnDefinition = "DECIMAL(15, 2)")
    private BigDecimal minLimitAsset;

    @Comment(value = "최대 입장 가능 인원")
    @Column
    private int maxLimitPlayer;

    @ElementCollection
    private List<String> playerEmailList = new ArrayList<>();

    @OneToMany(mappedBy = "house", cascade = CascadeType.REMOVE)
    private List<Game> gameList = new ArrayList<>();

    @Builder
    public House(Long houseId, String title, BigDecimal minLimitAsset, int maxLimitPlayer) {
        this.houseId = houseId;
        this.title = title;
        this.minLimitAsset = minLimitAsset;
        this.maxLimitPlayer = maxLimitPlayer;
    }
}
