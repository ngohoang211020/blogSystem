package com.blogsystem.service.impl;

import com.blogsystem.dto.auth.LoginRequest;
import com.blogsystem.dto.auth.LoginResponse;
import com.blogsystem.enums.TokenType;
import com.blogsystem.repository.UserRepository;
import com.blogsystem.security.service.TokenProvider;
import com.blogsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        var user = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        var accessToken = tokenProvider.generateAccessToken(user);
        var refreshToken = tokenProvider.generateRefreshToken(user);
        return new LoginResponse(accessToken, refreshToken, TokenType.JWT);
    }

}
