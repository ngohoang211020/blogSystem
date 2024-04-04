package com.blogsystem.service;

import com.blogsystem.dto.response.file.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    FileResponse uploadAttachment(MultipartFile file, int type) throws IOException;
}
