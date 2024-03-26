package com.blogsystem.config;

import com.blogsystem.cloudinary.CloudinaryUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class CloudinaryConfig {
    @Bean
    public CloudinaryUtil cloudinaryUtil(Environment environment) {
        return new CloudinaryUtil(environment);
    }
}


