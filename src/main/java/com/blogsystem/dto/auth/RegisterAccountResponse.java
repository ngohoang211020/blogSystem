package com.blogsystem.dto.auth;

import com.blogsystem.common.constant.BlogSystemErrorCode;
import com.blogsystem.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RegisterAccountResponse extends BaseResponse {
    private String email;
    private UUID userId;
    private String profilePictureUrl;
    public RegisterAccountResponse(int code, String email, UUID userId, String profilePictureUrl) {
        super(code);
        this.email = email;
        this.userId = userId;
        this.profilePictureUrl=profilePictureUrl;
    }

    public RegisterAccountResponse(String email, UUID userId, String profilePictureUrl) {
        super(BlogSystemErrorCode.SUCCESS_CODE);
        this.email = email;
        this.userId = userId;
        this.profilePictureUrl=profilePictureUrl;
    }
}
