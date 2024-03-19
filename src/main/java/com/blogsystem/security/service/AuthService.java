package com.blogsystem.security.service;

import com.blogsystem.enums.TokenType;
import com.blogsystem.security.dto.LoginRequest;
import com.blogsystem.security.dto.LoginResponse;
import com.blogsystem.security.jwt.TokenProvider;
import com.blogsystem.security.model.JwtUser;
import com.blogsystem.security.provider.DAOAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest loginRequest) {
        var user = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        var accessToken = tokenProvider.generateAccessToken(user);
        var refreshToken = tokenProvider.generateRefreshToken(user);
        return new LoginResponse(accessToken,refreshToken, TokenType.JWT);
    }
}
