package com.blogsystem.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.Map;

@Getter
public class CloudinaryUtil {
    private static final Logger log = LoggerFactory.getLogger(CloudinaryUtil.class);
    private final Cloudinary cloudinary;
    private final String uploadFolder;

    public CloudinaryUtil(Environment environment) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", environment.getRequiredProperty("cloudinary.cloud-name"),
                "api_key", environment.getRequiredProperty("cloudinary.key"),
                "api_secret", environment.getRequiredProperty("cloudinary.secret-key"),
                "secure", true));
        this.uploadFolder = environment.getRequiredProperty("cloudinary.upload-folder");
    }

    public CloudinaryResponse uploadImage(String folder,String publicId, byte[] bytes) throws IOException {
        var uploadResponse =  upload(folder,publicId,bytes);
        return buildUploadResponse(uploadResponse);
    }

    private Map<String,Object> upload(String folder,String publicId, byte[] bytes) throws IOException {
        try {
            return cloudinary.uploader().upload(bytes,
                    ObjectUtils.asMap(
                            "public_id",publicId,
                            "folder", folder
                    ));
        } catch (IOException e) {
            log.error("Failed to upload file to cloudinary. {} - {}", e.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
    }
    public String generateUrl(String publicId) {
        var request = cloudinary.url().transformation(
                new Transformation().width(200));
        return request.generate(publicId);
    }

    private CloudinaryResponse buildUploadResponse(Map<String, Object> uploadMapResp) {
        var cloudinaryResp = new CloudinaryResponse();
        cloudinaryResp.setUrl((String) uploadMapResp.getOrDefault("url", ""));
        cloudinaryResp.setPublicId((String) uploadMapResp.getOrDefault("public_id", ""));
        cloudinaryResp.setFileName((String) uploadMapResp.getOrDefault("",""));
        return cloudinaryResp;
    }
}
