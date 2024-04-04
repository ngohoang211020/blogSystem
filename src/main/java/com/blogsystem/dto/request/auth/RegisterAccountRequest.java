package com.blogsystem.dto.request.auth;

import com.blogsystem.annotation.Name;
import com.blogsystem.annotation.Password;
import com.blogsystem.annotation.Phone;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterAccountRequest {
    @Email
    private String email;

    @Name
    private String fullName;

    @Password
    private String password;

    @Password
    private String confirmPassword;

    @Phone
    private String phoneNumber;

    @NotBlank
    private String username;

    @NotBlank
    private String profilePicture;
}
