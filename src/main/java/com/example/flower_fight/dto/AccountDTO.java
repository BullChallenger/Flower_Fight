package com.example.flower_fight.dto;

import com.example.flower_fight.domain.Asset;
import com.example.flower_fight.domain.constant.AccountRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

public class AccountDTO implements Serializable {

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class AccountCacheDTO implements UserDetails {
        private Long accountId;

        private String email;
        private String nickName;
        private String password;
        private String name;

        private int age;

        private AccountRole role;

        private BigDecimal asset;

        private Timestamp createdAt;
        private Timestamp updatedAt;

        private Boolean isDeleted;

        @Builder
        public AccountCacheDTO(String email, String nickName, String password, String name, int age, AccountRole role, BigDecimal asset) {
            this.email = email;
            this.nickName = nickName;
            this.password = password;
            this.name = name;
            this.age = age;
            this.role = role;
            this.asset = asset;
        }

        @Override
        @JsonIgnore
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Set.of(new SimpleGrantedAuthority(this.getRole().toString()));
        }

        @Override
        @JsonIgnore
        public String getUsername() {
            return this.nickName;
        }

        @Override
        @JsonIgnore
        public boolean isAccountNonExpired() {
            return this.isDeleted;
        }

        @Override
        @JsonIgnore
        public boolean isAccountNonLocked() {
            return this.isDeleted;
        }

        @Override
        @JsonIgnore
        public boolean isCredentialsNonExpired() {
            return this.isDeleted;
        }

        @Override
        @JsonIgnore
        public boolean isEnabled() {
            return this.isDeleted;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CreateRequest {
        private String email;

        private String nickName;

        private String password;

        private String name;

        private int age;

        private BigDecimal asset = BigDecimal.valueOf(500_000_000);

        @Builder
        public CreateRequest(String email, String nickName, String password, String name, int age) {
            this.email = email;
            this.nickName = nickName;
            this.password = password;
            this.name = name;
            this.age = age;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class LoginRequest {
        private String email;
        private String password;

        @Builder
        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UpdateRequest {
        private Long accountId;

        private String email;

        private String nickName;

        private String password;

        private String name;

        private int age;

        @Builder
        public UpdateRequest(Long accountId, String email, String nickName, String password, String name, int age) {
            this.accountId = accountId;
            this.email = email;
            this.nickName = nickName;
            this.password = password;
            this.name = name;
            this.age = age;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteRequest {
        private Long accountId;

        private String password;

        @Builder
        public DeleteRequest(Long accountId, String password) {
            this.accountId = accountId;
            this.password = password;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CreateResponse {
        private Long accountId;

        private String nickName;

        @Builder
        public CreateResponse(Long accountId, String nickName) {
            this.accountId = accountId;
            this.nickName = nickName;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GetResponse {
        private Long accountId;

        private String email;

        private String nickName;

        private String password;

        private String name;

        private int age;

        @Builder
        public GetResponse(Long accountId, String email, String nickName, String password, String name, int age) {
            this.accountId = accountId;
            this.email = email;
            this.nickName = nickName;
            this.password = password;
            this.name = name;
            this.age = age;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class LoginResponse {
        private Long accountId;

        private String email;

        private String nickName;

        private String password;

        private String name;

        private int age;

        private String token;

        private BigDecimal asset;
    }
}
