package com.blogsystem.security.dto;

import com.blogsystem.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
}
