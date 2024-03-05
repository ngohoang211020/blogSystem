package com.blogsystem.security.common.utils;

import com.blogsystem.entity.UserEntity;
import com.blogsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUserUtils {
    private final UserRepository userRepository;
    public UserEntity getCurrentUser() {
        return userRepository.findByUsername(getCurrentUserName()).orElse(null);
    }

    private  String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
