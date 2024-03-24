package com.blogsystem.smtp;

import com.blogsystem.util.LexicalUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.CollectionUtils;

import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SmtpUtil {
    private static final Logger log = LoggerFactory.getLogger(SmtpUtil.class);
    private final JavaMailSender mailSender;
    private final SmtpProperties smtpProperties;

    private final SmtpTemplateFactory smtpTemplateFactory;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public SmtpUtil(JavaMailSender mailSender, SmtpProperties smtpProperties, SmtpTemplateFactory smtpTemplateFactory) {
        this.mailSender = mailSender;
        this.smtpProperties = smtpProperties;
        this.smtpTemplateFactory = smtpTemplateFactory;
        log.info("Initialized SES client");
    }

    public void sendEmail(String to, String subject, String message) throws MessagingException {
        this.sendEmail(smtpProperties.getDefaultSender(), to, subject, message);
    }

    public void sendEmail(String to, Collection<String> cc, Collection<String> bcc, String subject, String message) throws MessagingException {
        sendEmail(smtpProperties.getDefaultSender(), List.of(to), cc, bcc, subject, message);
    }


    public void sendEmail(String from, String to, String subject, String message) throws MessagingException {
        sendEmail(from, List.of(to), null, null, subject, message);
    }

    public void sendEmail(String from, Collection<String> to, Collection<String> cc, Collection<String> bcc, String subject, String message) throws MessagingException {
        var mimeMessage = mailSender.createMimeMessage();
        var mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        if (!CollectionUtils.isEmpty(to)) {
            mimeMessageHelper.setTo(LexicalUtil.collectionToArray(to));
        }

        if (!CollectionUtils.isEmpty(cc)) {
            mimeMessageHelper.setCc(LexicalUtil.collectionToArray(to));
        }

        if (!CollectionUtils.isEmpty(bcc)) {
            mimeMessageHelper.setBcc(LexicalUtil.collectionToArray(to));
        }
        mimeMessageHelper.setText(message, true);
        mimeMessageHelper.setSubject(subject);

        mailSender.send(mimeMessage);
        System.out.println("Mail Send...");

    }

    public String buildEmailBody(Object args, SmtpTemplate templateName) {
        var template = smtpTemplateFactory.getTemplate(templateName);

        var context = new VelocityContext(objectMapper.convertValue(args, new TypeReference<HashMap<String, Object>>() {
        }));

        var writer = new StringWriter();

        template.merge(context, writer);

        return writer.toString();
    }
}

