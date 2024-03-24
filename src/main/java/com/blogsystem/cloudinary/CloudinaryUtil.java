package com.blogsystem.cloudinary;

import com.blogsystem.smtp.SmtpProperties;
import com.blogsystem.smtp.SmtpTemplateFactory;
import com.blogsystem.smtp.SmtpUtil;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class CloudinaryUtil {
    private static final Logger log = LoggerFactory.getLogger(CloudinaryUtil.class);
    private final Cloudinary cloudinary;
    private final CloudinaryProperties cloudinaryProperties;

    public CloudinaryResponse uploadImage(Object image) throws IOException {
        var uploadResponse= cloudinary.uploader().upload(image,ObjectUtils.emptyMap());
        return buildResponse(uploadResponse);
    }

    private CloudinaryResponse buildResponse(Map<String,Object> uploadMapResp){
        var cloudinaryResp = new CloudinaryResponse();
        cloudinaryResp.setUrl((String) uploadMapResp.getOrDefault("url",""));
        cloudinaryResp.setPublicId((String) uploadMapResp.getOrDefault("public_id",""));
        return cloudinaryResp;
    }
}
