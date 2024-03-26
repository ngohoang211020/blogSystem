package com.blogsystem.controller.auth;

import com.blogsystem.common.constant.BlogSystemErrorCode;
import com.blogsystem.common.response.APIResponse;
import com.blogsystem.dto.auth.*;
import com.blogsystem.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v2/users")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final ObjectMapper objectMapper;
    @PostMapping()
    public APIResponse<RegisterAccountResponse> register(@RequestParam("registerBody") String request, @RequestParam("profilePicture")MultipartFile profilePicture ) throws IOException {
        var registerResp = new RegisterAccountResponse();
        try {
            var registerAccountRequest = objectMapper.readValue(request,RegisterAccountRequest.class);
            registerAccountRequest.setProfilePicture(profilePicture);
            registerResp = accountService.register(registerAccountRequest);
        } catch (JsonProcessingException e){
            registerResp.setCode(BlogSystemErrorCode.ERROR_BAD_REQUEST_CODE);
        }
        return new APIResponse<>(registerResp, HttpStatus.OK);
    }

    @PutMapping("/verify-otp")
    public APIResponse<VerifyOTPResponse> verifyOTP(@RequestBody VerifyOTPRequest request) {
        var verifyResp = accountService.verifyOTP(request);
        return new APIResponse<>(verifyResp,HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public APIResponse<CurrentUserResponse> currentUser() {
        var currentUser = accountService.getCurrentUser();
        return new APIResponse<>(currentUser, HttpStatus.OK);
    }
}
