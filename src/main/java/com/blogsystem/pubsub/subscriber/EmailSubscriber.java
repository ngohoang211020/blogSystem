package com.blogsystem.pubsub.subscriber;

import com.blogsystem.pubsub.event.OTPRegistrationEmailEvent;
import com.blogsystem.smtp.SmtpProperties;
import com.blogsystem.smtp.SmtpTemplate;
import com.blogsystem.smtp.SmtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class EmailSubscriber {
    private final SmtpUtil smtpUtil;
    private final SmtpProperties smtpProperties;
    @Async
    @EventListener
    public void sendEmailSubscriber(OTPRegistrationEmailEvent event) throws MessagingException {
        var content = smtpUtil.buildEmailBody(event, SmtpTemplate.OTP_REGISTRATION);
        smtpUtil.sendEmail(event.getEmail(), null,
                null, "OTP Registration", content);
        log.info("Send mail successfully");
    }
}
