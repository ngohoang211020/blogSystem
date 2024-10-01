package com.blogsystem.cronjob;

import com.blogsystem.cloudinary.dto.AccountPictureUtil;
import com.blogsystem.pubsub.publisher.EmailPublisher;
import com.blogsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@RequiredArgsConstructor
@Log4j2
public class TestTask2 implements Runnable{
    private final UserRepository userRepository;
    private final EmailPublisher emailPublisher;
    private final AccountPictureUtil accountPictureUtil;
    @Override
    public void run() {
//        System.out.println("Current thread: "+Thread.currentThread().getName() + " at time: "+System.currentTimeMillis() + this.getClass().getName());
    }
}
