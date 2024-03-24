package com.blogsystem.config;

import com.blogsystem.cloudinary.CloudinaryProperties;
import com.blogsystem.cloudinary.CloudinaryUtil;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class CloudinaryConfig {
    @Bean
    public CloudinaryProperties getCloudinaryProperties(Environment environment) {
        return new CloudinaryProperties(environment);
    }
    @Bean
    public Cloudinary cloudinary(CloudinaryProperties properties) {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", properties.getCloudinaryName(),
                "api_key", properties.getCloudinaryKey(),
                "api_secret", properties.getCloudinarySecret(),
                "secure", true));
    }
    @Bean
    public CloudinaryUtil cloudinaryUtil(Cloudinary cloudinary,CloudinaryProperties properties) {
        return new CloudinaryUtil(cloudinary,properties);
    }


}


