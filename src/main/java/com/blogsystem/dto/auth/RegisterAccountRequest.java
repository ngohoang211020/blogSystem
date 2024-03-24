package com.blogsystem.dto.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterAccountRequest {
    private String email;
    private String fullName;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
}
