package com.blogsystem.smtp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@ConfigurationProperties("spring.mail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmtpConfiguration {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private String defaultSender;

    private List<String> defaultCcEmails;

    private String host;

    private String port;

    private String username;

    private String password;
}

