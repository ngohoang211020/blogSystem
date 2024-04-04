package com.blogsystem.dto.response.auth;

import com.blogsystem.dto.response.BaseResponse;
import com.blogsystem.enums.ServiceErrorDesc;
import com.blogsystem.enums.TokenType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse extends BaseResponse {
    private String accessToken;
    private String refreshToken;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    public LoginResponse(int code, String accessToken, String refreshToken, TokenType tokenType) {
        super(code);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
    }

    public LoginResponse( String accessToken, String refreshToken, TokenType tokenType) {
        super(ServiceErrorDesc.SUCCESS.getVal());
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
    }
}
