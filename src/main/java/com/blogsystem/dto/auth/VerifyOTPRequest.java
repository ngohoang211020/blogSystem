package com.blogsystem.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOTPRequest {
    private String otp;
    private String email;
}
