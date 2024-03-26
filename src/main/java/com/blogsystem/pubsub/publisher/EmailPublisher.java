package com.blogsystem.pubsub.publisher;

import com.blogsystem.pubsub.event.OTPRegistrationEmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailPublisher {
    private final ApplicationEventPublisher publisher;

    public void publishSendEmailEvent(OTPRegistrationEmailEvent event) {
        publisher.publishEvent(event);
    }
}
