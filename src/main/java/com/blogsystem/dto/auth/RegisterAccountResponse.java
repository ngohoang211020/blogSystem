package com.blogsystem.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class RegisterAccountResponse {
    private String email;
    private UUID userId;
}
