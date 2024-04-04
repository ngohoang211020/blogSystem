package com.blogsystem.common.response;

import com.blogsystem.dto.response.BaseResponse;
import com.blogsystem.enums.ServiceErrorDesc;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class APIResponse<T extends BaseResponse> extends ResponseEntity {

    public APIResponse(HttpStatus status) {
        this(APIBody.builder().code(ServiceErrorDesc.SUCCESS.getVal()).build(), null, status);
    }

    public APIResponse(T body, HttpStatus status) {
        this(APIBody.builder().code(body.getCode()).data(body).build(), null, status);
    }

    public APIResponse(int code, T body) {
        this(APIBody.builder().code(code).data(body).build(), null, HttpStatus.OK);
    }

    public APIResponse(int code, T body, HttpStatus httpStatus) {
        this(APIBody.builder().code(code).data(body).build(), null, httpStatus);
    }

    public APIResponse(int code, T body, MultiValueMap<String, String> headers, HttpStatus httpStatus) {
        this(APIBody.builder().code(code).data(body).build(), headers, httpStatus);
    }

    public APIResponse(APIBody body, MultiValueMap<String, String> headers, HttpStatus httpStatus) {
        super(body, headers, httpStatus);
    }

    public static <T extends BaseResponse> APIResponse<T> okStatus(T body) {
        return new APIResponse<T>(body, HttpStatus.OK);
    }

    public static <T extends BaseResponse> APIResponse<T> okStatus() {
        return new APIResponse<T>(HttpStatus.OK);
    }

    public static <T extends BaseResponse> APIResponse<T> okStatus(T body, int code) {
        return new APIResponse<T>(code, body, HttpStatus.OK);
    }

    public static <T extends BaseResponse> APIResponse<T> errorStatus(T body, HttpStatus httpStatus) {
        return new APIResponse<T>(body, httpStatus);
    }

    public static <T extends BaseResponse> APIResponse<T> errorStatus(int code, T body, HttpStatus httpStatus) {
        return new APIResponse<T>(code, body, httpStatus);
    }

    @Getter
    @Setter
    @Builder
    public static class APIBody<T> {
        int code;
        T data;
    }
}
