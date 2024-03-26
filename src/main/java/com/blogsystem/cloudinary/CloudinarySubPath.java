package com.blogsystem.cloudinary;

public enum CloudinarySubPath {
    ACCOUNT("blogsystem/account/picture");
    public final String content;

    CloudinarySubPath(String content) {
        this.content = content;
    }
}
