package com.blogsystem.cloudinary;

public enum CloudinarySubPath {
    ACCOUNT(1,"blogsystem/account/picture");
    public final String content;
    public final int type;

    CloudinarySubPath(int type, String content) {
        this.type = type;
        this.content = content;
    }
}
