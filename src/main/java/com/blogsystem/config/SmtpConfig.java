package com.blogsystem.config;

import com.blogsystem.smtp.SmtpProperties;
import com.blogsystem.smtp.SmtpTemplateFactory;
import com.blogsystem.smtp.SmtpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class SmtpConfig {
    @Bean
    public MailProperties getMailProperties() {
        return new MailProperties();
    }

    @Bean
    public SmtpProperties smtpConfiguration(Environment environment) {
        return new SmtpProperties(environment);
    }

    //TODO: JavaMailSender is not autoconfigured properties,
    // had to do it manually. need more investigate to find out the root cause
    @Bean
    public JavaMailSender javaMailSender() {
        var mailProperties = getMailProperties();
        var javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailProperties.getHost());
        javaMailSender.setUsername(mailProperties.getUsername());
        javaMailSender.setPort(mailProperties.getPort());
        javaMailSender.setPassword(mailProperties.getPassword());
        javaMailSender.setProtocol(mailProperties.getProtocol());
        javaMailSender.setJavaMailProperties(properties());
        return javaMailSender;
    }

    @Bean
    public SmtpTemplateFactory smtpTemplateFactory() {
        return new SmtpTemplateFactory();
    }

    @Bean
    public SmtpUtil smtpUtil(JavaMailSender javaMailSender, SmtpProperties smtpProperties, SmtpTemplateFactory smtpTemplateFactory, ObjectMapper objectMapper) {
        return new SmtpUtil(javaMailSender, smtpProperties, smtpTemplateFactory,objectMapper);
    }

    private Properties properties() {
        var properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "false");
        return properties;
    }
}
