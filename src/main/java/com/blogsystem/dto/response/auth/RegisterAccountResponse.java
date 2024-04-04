package com.blogsystem.dto.response.auth;

import com.blogsystem.dto.response.BaseResponse;
import com.blogsystem.enums.ServiceErrorDesc;
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
        super(ServiceErrorDesc.SUCCESS.getVal());
        this.email = email;
        this.userId = userId;
        this.profilePictureUrl=profilePictureUrl;
    }
}
