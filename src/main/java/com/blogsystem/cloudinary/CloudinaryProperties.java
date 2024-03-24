package com.blogsystem.cloudinary;

import lombok.Getter;
import org.springframework.core.env.Environment;

@Getter
public class CloudinaryProperties {
    private final String cloudinaryName;
    private final String cloudinaryKey;
    private final String cloudinarySecret;
    private final String cloudinaryUploadFolder;

    public CloudinaryProperties(Environment environment) {
        this.cloudinaryName = environment.getRequiredProperty("cloudinary.cloud-name");
        this.cloudinaryKey = environment.getRequiredProperty("cloudinary.key");
        this.cloudinarySecret = environment.getRequiredProperty("cloudinary.secret-key");
        this.cloudinaryUploadFolder = environment.getRequiredProperty("cloudinary.upload-folder");
    }
}
