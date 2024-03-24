package com.blogsystem.smtp;

import lombok.Getter;
import org.springframework.core.env.Environment;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class SmtpProperties {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final String defaultSender;

    private final List<String> defaultCcEmails;

    private final String host;

    private final String port;

    private final String username;

    private final String password;
    public SmtpProperties(Environment env) {
        this.defaultSender = env.getRequiredProperty("spring.mail.default-sender");
        this.defaultCcEmails = Arrays.stream(Objects.requireNonNullElse(env.getProperty("spring.mail.default-cc-email", String.class), "")
                        .split(","))
                .collect(Collectors.toList());
        this.host = env.getRequiredProperty("spring.mail.host");
        this.port = env.getRequiredProperty("spring.mail.port");
        this.username = env.getRequiredProperty("spring.mail.username");
        this.password = env.getRequiredProperty("spring.mail.password");
    }
}

