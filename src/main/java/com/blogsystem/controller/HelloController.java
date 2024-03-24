package com.blogsystem.controller;

import com.blogsystem.cloudinary.CloudinaryResponse;
import com.blogsystem.cloudinary.CloudinaryUtil;
import com.blogsystem.common.ResponseBody;
import com.blogsystem.security.util.CurrentUserUtils;
import com.blogsystem.smtp.SmtpProperties;
import com.blogsystem.smtp.SmtpTemplate;
import com.blogsystem.smtp.SmtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/kaka")
@RequiredArgsConstructor
public class HelloController {
    private final SmtpUtil smtpUtil;
    private final SmtpProperties smtpProperties;
    private final CloudinaryUtil cloudinaryUtil;

    @GetMapping
    public ResponseBody<String> hello() throws MessagingException {
        var currentUser = CurrentUserUtils.getCurrentUser();
        assert currentUser != null;
        var otpMap = new HashMap<String, Object>();
        otpMap.put("fullname", currentUser.getName());
        otpMap.put("otp", "1213131");
        otpMap.put("companyName", "sksinhCompany");
        otpMap.put("expiredTime", "24-03-2024");
        var content = smtpUtil.buildEmailBody(otpMap, SmtpTemplate.OTP_REGISTRATION);

        smtpUtil.sendEmail(smtpProperties.getUsername(), null,
                null, "OTP Registration", content);
        return new ResponseBody<>(HttpStatus.OK, currentUser.getName(), "Authorized successfully");
    }

    @PostMapping
    public ResponseBody<CloudinaryResponse> testCloudinary(@RequestParam("file") MultipartFile file) throws MessagingException, IOException {
        var uploadResponse = cloudinaryUtil.uploadImage(file.getBytes());
        return new ResponseBody<>(HttpStatus.OK, uploadResponse, "Upload file successfully");
    }
}
