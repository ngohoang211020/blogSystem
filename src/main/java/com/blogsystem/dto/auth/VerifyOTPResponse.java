package com.blogsystem.dto.auth;

import com.blogsystem.dto.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VerifyOTPResponse extends BaseResponse {
    private boolean verified;

    public VerifyOTPResponse(int code, boolean verified) {
        super(code);
        this.verified = verified;
    }

    public VerifyOTPResponse(boolean verified) {
        super(BlogSystemErrorCode.SUCCESS_CODE);
        this.verified = verified;
    }
}
