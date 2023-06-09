package com.example.flower_fight.domain;

import com.example.flower_fight.domain.constant.AccountRole;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@ToString
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted=false")
public class Account extends BaseEntity {

    @Id
    @Column(name = "account_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Comment(value = "이메일")
    @Column(nullable = false)
    private String email;

    @Comment(value = "닉네임")
    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Comment(value = "비밀번호")
    @Column(nullable = false)
    private String password;

    @Comment(value = "이름")
    @Column(nullable = false)
    private String name;

    @Comment(value = "나이")
    @Column(nullable = false)
    private int age;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private AccountRole role = AccountRole.USER;

    @Builder
    public Account(Long accountId, String email, String nickName, String password, String name, int age) {
        this.accountId = accountId;
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.name = name;
        this.age = age;
    }
}
