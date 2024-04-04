package com.blogsystem.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class VerifyOTPRequest {
    @NotBlank
    private String otp;

    @Email
    private String email;
}
