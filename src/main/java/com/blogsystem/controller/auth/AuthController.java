package com.blogsystem.controller.auth;

import com.blogsystem.common.ResponseBody;
import com.blogsystem.dto.auth.LoginRequest;
import com.blogsystem.dto.auth.LoginResponse;
import com.blogsystem.dto.auth.RegisterAccountResponse;
import com.blogsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseBody<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var token = authService.login(loginRequest);
        return new ResponseBody<>(HttpStatus.OK, token);
    }

    @PostMapping("/register")
    public ResponseBody<RegisterAccountResponse> logout() {
        return new ResponseBody<>(HttpStatus.OK, null, "Success");
    }
}
