package com.blogsystem.dto.auth;

import com.blogsystem.common.constant.BlogSystemErrorCode;
import com.blogsystem.dto.BaseResponse;
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
        super(BlogSystemErrorCode.SUCCESS_CODE);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
    }
}
