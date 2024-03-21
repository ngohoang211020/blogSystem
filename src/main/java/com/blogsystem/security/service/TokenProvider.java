package com.blogsystem.security.service;

import org.springframework.security.core.Authentication;

public interface TokenProvider {
    String generateAccessToken(Authentication authentication);

    String generateRefreshToken(Authentication authentication);

    boolean validateToken(String token);

    Authentication getAuthentication(String token);
}
