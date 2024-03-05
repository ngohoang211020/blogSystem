package com.blogsystem.security;

import com.blogsystem.security.jwt.filter.JwtAuthorizationFilter;
import com.blogsystem.security.provider.DAOAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.blogsystem.security.common.constants.SecurityConstants.SYSTEM_WHITELIST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final DAOAuthenticationProvider daoAuthenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // disabling csrf since we won't use form login
                .csrf().disable()
                // giving permission to every request for /login endpoint
                .authorizeRequests().antMatchers(HttpMethod.POST, SYSTEM_WHITELIST).permitAll()
                // for everything else, the user has to be authenticated
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authenticationProvider(daoAuthenticationProvider);

        return http.build();
    }

}


