package com.blogsystem.service.impl;

import com.blogsystem.dto.auth.RegisterAccountRequest;
import com.blogsystem.dto.auth.RegisterAccountResponse;
import com.blogsystem.entity.UserEntity;
import com.blogsystem.enums.StatusType;
import com.blogsystem.enums.TokenType;
import com.blogsystem.dto.auth.LoginRequest;
import com.blogsystem.dto.auth.LoginResponse;
import com.blogsystem.repository.UserRepository;
import com.blogsystem.security.service.TokenProvider;
import com.blogsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public LoginResponse login(LoginRequest loginRequest) {
        var user = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        var accessToken = tokenProvider.generateAccessToken(user);
        var refreshToken = tokenProvider.generateRefreshToken(user);
        return new LoginResponse(accessToken,refreshToken, TokenType.JWT);
    }

    @Override
    public RegisterAccountResponse register(RegisterAccountRequest request) {
        var user = buildUser(request);
        user = userRepository.save(user);
        return new RegisterAccountResponse(user.getEmail(),user.getUserId());
    }

    private UserEntity buildUser(RegisterAccountRequest request){
        var user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setStatus(StatusType.INACTIVE.getValue());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        return user;
    }
}
