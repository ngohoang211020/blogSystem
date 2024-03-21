package com.blogsystem.security.service;

import com.blogsystem.dto.auth.LoginRequest;
import com.blogsystem.dto.auth.LoginResponse;

public interface AuthService {
    public LoginResponse login(LoginRequest loginRequest);
}
