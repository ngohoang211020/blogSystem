package com.blogsystem.service;

import com.blogsystem.dto.auth.*;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
