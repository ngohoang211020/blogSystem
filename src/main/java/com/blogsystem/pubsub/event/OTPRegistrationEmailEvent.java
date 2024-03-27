package com.blogsystem.pubsub.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

@Getter
@Setter
public class OTPRegistrationEmailEvent extends ApplicationEvent {
    private String username;
    private String email;
    private int duration;
    private String otp;
    private String profilePicture;

    public OTPRegistrationEmailEvent(Object source) {
        super(source);
    }
}
