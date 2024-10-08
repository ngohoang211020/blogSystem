package com.blogsystem.controller.auth;

import com.blogsystem.common.response.APIResponse;
import com.blogsystem.dto.request.auth.RegisterAccountRequest;
import com.blogsystem.dto.request.auth.VerifyOTPRequest;
import com.blogsystem.dto.response.auth.CurrentUserResponse;
import com.blogsystem.dto.response.auth.RegisterAccountResponse;
import com.blogsystem.dto.response.auth.VerifyOTPResponse;
import com.blogsystem.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v2/users")
@Tag(
        name = "Account Controller",
        description = "This controller is used for managing account"
)
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "Register account")
    @PostMapping
    public APIResponse<RegisterAccountResponse> register(@RequestBody RegisterAccountRequest registerAccountRequest) throws IOException {
        var registerResp = accountService.register(registerAccountRequest);
        return new APIResponse<>(registerResp, HttpStatus.OK);
    }

    @Operation(summary = "Verify OTP which is received from email")
    @PutMapping("/verify-otp")
    public APIResponse<VerifyOTPResponse> verifyOTP(@RequestBody VerifyOTPRequest request) {
        var verifyResp = accountService.verifyOTP(request);
        return new APIResponse<>(verifyResp, HttpStatus.OK);
    }

    @Operation(summary = "Get logged user information")
    @GetMapping("/current-user")
    public APIResponse<CurrentUserResponse> currentUser() {
        var currentUser = accountService.getCurrentUser();
        return new APIResponse<>(currentUser, HttpStatus.OK);
    }
}
