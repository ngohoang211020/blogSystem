package com.blogsystem.controller.auth;

import com.blogsystem.common.response.APIResponse;
import com.blogsystem.dto.request.auth.LoginRequest;
import com.blogsystem.dto.response.auth.LoginResponse;
import com.blogsystem.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
@Tag(
        name = "Auth Controller",
        description = "This controller is included login API"
)
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Login API and return access token if logging successfully")
    @PostMapping("/login")
    public APIResponse<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        var token = authService.login(loginRequest);
        return new APIResponse<>(token, HttpStatus.OK);
    }
}
