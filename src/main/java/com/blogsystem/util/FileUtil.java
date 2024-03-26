package com.blogsystem.util;

import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;

public class FileUtil {
    public static String getFilenameWithoutExtension(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            throw new IllegalArgumentException("Invalid filename was given. Must not blank");
        }

        return fileName.indexOf(".") > 0 ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;
    }

    public static String getFileExtension(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            throw new IllegalArgumentException("Invalid filename was given. Must not blank");
        }

        return fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".") + 1) : "";
    }

    public static String normalizeFilename(String input) {
        String result = Normalizer.normalize(input, Normalizer.Form.NFD);
        result = result.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        result = result.replace('đ', 'd');
        result = result.replace('Đ', 'D');
        result = result.replaceAll("[^a-z A-Z0-9-_.]", "");
        result = result.replaceAll(" ", "-");
        return result;
    }

    public static String getStaticFileUrl(String apiBaseUrl, String filename) {
        return String.format("%s/api/v2/files/static?name=%s", apiBaseUrl, filename);
    }

    public static String extractFileNameFromS3Url(String url) {
        var excludedQueryStr = url.substring(0, url.indexOf("?"));
        return excludedQueryStr.substring(excludedQueryStr.lastIndexOf("/"));
    }

    public static String guessContentTypeFromCloudinaryUrl(String url) {
        var filename = extractFileNameFromS3Url(url);

        if (filename.endsWith("png")) {
            return "image/png";
        } else if (filename.endsWith(".jpeg") || filename.endsWith(".jpg")) {
            return "image/jpeg";
        } else if (filename.endsWith("csv")) {
            return "text/csv";
        } else if (filename.endsWith("doc")) {
            return "application/msword";
        } else if (filename.endsWith("docx")) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else if (filename.endsWith("pdf")) {
            return "application/pdf";
        } else {
            return "application/octet-stream";
        }
    }

}
