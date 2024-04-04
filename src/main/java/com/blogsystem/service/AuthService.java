package com.blogsystem.service;

import com.blogsystem.dto.request.auth.LoginRequest;
import com.blogsystem.dto.response.auth.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
