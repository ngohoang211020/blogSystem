package com.blogsystem.cloudinary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CloudinaryResponse {
    private String url;
    private String publicId;
    private String fileName;
}
