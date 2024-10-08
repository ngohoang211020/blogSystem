package com.blogsystem.dto.response.auth;

import com.blogsystem.dto.response.BaseResponse;
import com.blogsystem.enums.ServiceErrorDesc;
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
        super(ServiceErrorDesc.SUCCESS.getVal());
        this.verified = verified;
    }
}
