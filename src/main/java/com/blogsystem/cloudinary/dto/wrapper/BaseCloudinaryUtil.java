package com.blogsystem.cloudinary.dto.wrapper;

import com.blogsystem.cloudinary.CloudinarySubPath;
import com.blogsystem.cloudinary.CloudinaryUtil;
import com.blogsystem.util.FileUtil;
import org.apache.commons.lang3.StringUtils;

public abstract class BaseCloudinaryUtil {
    protected final CloudinaryUtil cloudinaryUtil;
    protected final CloudinarySubPath subPath;

    protected BaseCloudinaryUtil(CloudinaryUtil cloudinaryUtil, CloudinarySubPath subPath) {
        this.cloudinaryUtil = cloudinaryUtil;
        this.subPath = subPath;
    }

    public String presign(String filename) {
        if (StringUtils.isBlank(filename)) {
            return "";
        }
        var fileNameWithoutExtension = FileUtil.getFilenameWithoutExtension(filename);
        return cloudinaryUtil.generateUrl(normalizePublicId(fileNameWithoutExtension));
    }
    protected String normalizePublicId(String key) {
        return String.format("%s%s", subPath.content, key.startsWith("/") ? key : "/" + key);
    }
}
