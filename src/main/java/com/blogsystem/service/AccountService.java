package com.blogsystem.service;

import com.blogsystem.dto.auth.*;

import java.io.IOException;

public interface AccountService {
    RegisterAccountResponse register(RegisterAccountRequest registerAccountRequest) throws IOException;
    VerifyOTPResponse verifyOTP(VerifyOTPRequest verifyOTPRequest);
    CurrentUserResponse getCurrentUser();

}
