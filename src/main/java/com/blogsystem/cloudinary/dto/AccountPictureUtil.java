package com.blogsystem.cloudinary.dto;

import com.blogsystem.cloudinary.CloudinarySubPath;
import com.blogsystem.cloudinary.CloudinaryUtil;
import com.blogsystem.cloudinary.dto.wrapper.BaseCloudinaryUtil;
import org.springframework.stereotype.Component;

@Component
public class AccountPictureUtil extends BaseCloudinaryUtil {
    protected AccountPictureUtil(CloudinaryUtil cloudinaryUtil) {
        super(cloudinaryUtil, CloudinarySubPath.ACCOUNT);
    }
}
