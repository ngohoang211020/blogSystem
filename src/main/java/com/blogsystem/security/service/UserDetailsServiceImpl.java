package com.blogsystem.security.service;

import com.blogsystem.entity.UserEntity;
import com.blogsystem.repository.RoleRepository;
import com.blogsystem.repository.UserRepository;
import com.blogsystem.security.model.JwtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity= userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("The user name or password is not correct.")
        );
        return build(userEntity);
    }

    private JwtUser build(UserEntity user) {
        var roleCodes=roleRepository.findAllRoleCodeByUserId(user.getUserId());
        List<GrantedAuthority> authorities = roleCodes.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new JwtUser(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                user.getUsername(),
                user.getStatus(),
                authorities);
    }
}
