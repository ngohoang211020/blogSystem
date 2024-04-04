package com.blogsystem.dto.response.file;

import com.blogsystem.dto.response.BaseResponse;
import com.blogsystem.enums.ServiceErrorDesc;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileResponse extends BaseResponse {
    private String url;
    private String fileName;
    public FileResponse(int code, String url, String fileName) {
        super(code);
        this.url = url;
        this.fileName = fileName;
    }

    public FileResponse( String url, String fileName) {
        super(ServiceErrorDesc.SUCCESS.getVal());
        this.url = url;
        this.fileName = fileName;
    }
}
