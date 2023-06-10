package com.example.flower_fight.filter;

import com.example.flower_fight.dto.AccountDTO.*;
import com.example.flower_fight.service.AccountCacheService;
import com.example.flower_fight.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AccountCacheService accountCacheService;

    private final String secretKey;

    private static final String[] WHITE_LIST = {};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (Arrays.asList(WHITE_LIST).contains(request.getRequestURI())) {
                filterChain.doFilter(request, response);
                return;
            }

            final String token;
            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (header == null || !header.startsWith(JwtUtils.BEARER)) {
                // TODO: Implement Error
            }

            token = header.split(" ")[1].trim();
            log.info("Token Info : {}", token);

            if (JwtUtils.isExpired(token, secretKey)) {
                // TODO: Implement Error
            }

            String email = JwtUtils.extractEmail(token, secretKey);

            AccountCacheDTO theAccount = accountCacheService.loadAccountByEmail(email);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    theAccount, null, theAccount.getAuthorities()
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (RuntimeException e) {
            // TODO: Implement Error
        }

        filterChain.doFilter(request, response);
    }
}
