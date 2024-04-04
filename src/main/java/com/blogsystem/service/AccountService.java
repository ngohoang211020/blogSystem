package com.blogsystem.service;

import com.blogsystem.dto.request.auth.RegisterAccountRequest;
import com.blogsystem.dto.request.auth.VerifyOTPRequest;
import com.blogsystem.dto.response.auth.CurrentUserResponse;
import com.blogsystem.dto.response.auth.RegisterAccountResponse;
import com.blogsystem.dto.response.auth.VerifyOTPResponse;

import java.io.IOException;

public interface AccountService {
    RegisterAccountResponse register(RegisterAccountRequest registerAccountRequest) throws IOException;

    VerifyOTPResponse verifyOTP(VerifyOTPRequest verifyOTPRequest);

    CurrentUserResponse getCurrentUser();

}
