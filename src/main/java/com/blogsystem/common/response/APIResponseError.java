package com.blogsystem.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@Builder
public class APIResponseError implements Serializable {
    private static final long serialVersionUID = 1L;

    public long timestamp;
    public int code;
    public String error;
    public Object message;

    public APIResponseError() {
    }

    public APIResponseError(int code, Object message, String error) {
        this.timestamp = new Date().getTime();
        this.code = code;
        this.message = message;
        this.error = error;
    }
    public APIResponseError(long timestamp, int code, String error, Object message) {
        this.timestamp = timestamp;
        this.code = code;
        this.error = error;
        this.message = message;
    }
}
