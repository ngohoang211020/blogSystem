package com.blogsystem.dto.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterAccountRequest {
    private String email;
    private String fullName;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String username;
    private MultipartFile profilePicture;
}
