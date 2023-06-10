package com.example.flower_fight.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtUtils {

    private static final String EMAIL_CLAIM = "email";
    private static final String NICK_NAME_CLAIM = "nickName";
    public static final String BEARER = "Bearer ";

    private static Key getKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String generateToke(String email, String nickName, String secretKey, long expiredTime) {
        Claims claims = Jwts.claims();
        claims.put(EMAIL_CLAIM, email);
        claims.put(NICK_NAME_CLAIM, nickName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(getKey(secretKey), SignatureAlgorithm.HS512)
                .compact();
    }

    public static String extractEmail(String token, String secretKey) {
        return extractClaim(token, secretKey).get(EMAIL_CLAIM, String.class);
    }

    public static String extractNickName(String token, String secretKey) {
        return extractClaim(token, secretKey).get(NICK_NAME_CLAIM, String.class);
    }

    public static boolean isExpired(String token, String secretKey) {
        Date expiredDate = extractClaim(token, secretKey).getExpiration();
        return expiredDate.before(new Date());
    }

    private static Claims extractClaim(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
