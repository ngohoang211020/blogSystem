package com.blogsystem.common;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResponseBody<T> {
    private int httpStatus;
    private T data;
    private String message;

    public ResponseBody(HttpStatus httpStatus, T data, String message) {
        this.httpStatus = httpStatus.value();
        this.data = data;
        this.message = message;
    }
    public ResponseBody(HttpStatus httpStatus, T data) {
        this.httpStatus = httpStatus.value();
        this.data = data;
    }
}
