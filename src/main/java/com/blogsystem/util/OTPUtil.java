package com.blogsystem.util;

import java.util.Random;

public class OTPUtil {
    private static final int OTP_LENGTH = 6;

    public static String generateOTP() {
        var random = new Random();
        var otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
