package com.blogsystem.controller.file;

import com.blogsystem.cloudinary.CloudinaryUtil;
import com.blogsystem.common.response.APIResponse;
import com.blogsystem.dto.file.FileResponse;
import com.blogsystem.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v2/files")
@RequiredArgsConstructor
@Tag(
        name = "File Controller",
        description = "This controller is included upload attachment"
)
public class FileController {
    private final FileService fileService;

    @Operation(summary = "Upload attachment")
    @PostMapping
    public APIResponse<FileResponse> UploadAttachment(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("type") int type) throws IOException {
      var fileUpload = fileService.uploadAttachment(file,type);
      return new APIResponse<>(fileUpload, HttpStatus.OK);
    }
}
