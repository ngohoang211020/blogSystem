package com.blogsystem.security.auth_controller;

import com.blogsystem.security.dto.LoginRequest;
import com.blogsystem.security.dto.LoginResponse;
import com.blogsystem.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var token = authService.login(loginRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
//        authService.removeToken();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
