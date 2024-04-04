package com.blogsystem.service.impl;

import com.blogsystem.cloudinary.CloudinarySubPath;
import com.blogsystem.cloudinary.CloudinaryUtil;
import com.blogsystem.dto.response.file.FileResponse;
import com.blogsystem.service.FileService;
import com.blogsystem.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final CloudinaryUtil cloudinaryUtil;

    @Override
    public FileResponse uploadAttachment(MultipartFile file, int type) throws IOException {
        String folder;
        if (type == CloudinarySubPath.ACCOUNT.type) {
            folder = CloudinarySubPath.ACCOUNT.content;
        }
        var uploadResp = cloudinaryUtil.uploadImage(
                CloudinarySubPath.ACCOUNT.content,
                FileUtil.getFilenameWithoutExtension(file.getOriginalFilename()),
                file.getBytes());
        return new FileResponse(uploadResp.getUrl(), file.getOriginalFilename());
    }


}
