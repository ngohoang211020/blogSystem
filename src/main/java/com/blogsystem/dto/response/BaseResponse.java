package com.blogsystem.dto.response;

import com.blogsystem.enums.ServiceErrorDesc;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public abstract class BaseResponse implements Serializable {
    @JsonIgnore
    private int code;

    public BaseResponse(int code) {
        this.code = code;
    }

    public BaseResponse() {
        this.code= ServiceErrorDesc.SUCCESS.getVal();
    }
}
