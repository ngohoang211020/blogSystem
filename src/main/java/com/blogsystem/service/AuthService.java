package com.blogsystem.service;

import com.blogsystem.dto.auth.LoginRequest;
import com.blogsystem.dto.auth.LoginResponse;
import com.blogsystem.dto.auth.RegisterAccountRequest;
import com.blogsystem.dto.auth.RegisterAccountResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    RegisterAccountResponse register(RegisterAccountRequest registerAccountRequest);
}
