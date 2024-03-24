package com.blogsystem.smtp;

import lombok.Getter;

@Getter
public enum SmtpTemplate {
    OTP_REGISTRATION("otp-register-account");

    private final String filename;

    SmtpTemplate(String filename) {
        this.filename = filename;
    }

}
