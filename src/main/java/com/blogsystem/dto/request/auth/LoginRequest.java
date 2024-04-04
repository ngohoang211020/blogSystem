package com.blogsystem.dto.request.auth;

import com.blogsystem.annotation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginRequest {
    @Email(message = "Email is invalid")
    private String username;

    @Password
    private String password;
}
