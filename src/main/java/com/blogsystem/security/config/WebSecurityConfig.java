package com.blogsystem.security.config;

import com.blogsystem.security.filter.JwtAuthorizationFilter;
import com.blogsystem.security.filter.RateLimitFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.blogsystem.security.constants.SecurityConstants.SYSTEM_WHITELIST;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final AuthenticationEntryPoint unauthorizedHandler;
    private final AccessDeniedHandler accessDeniedHandler;
    private final RateLimitFilter rateLimitFilter;
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Origin", "Content-Type", "Accept"));
        configuration.setExposedHeaders(Arrays.asList("Content-Type", "X-Rate-Limit-Retry-After-Seconds", "X-Rate-Limit-Remaining"));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                // disabling csrf since we won't use form login
                // giving permission to every request for /login endpoint
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(SYSTEM_WHITELIST.toArray(String[]::new)).permitAll()
//                                .requestMatchers("/api/v2/users").hasRole("SUPER_ADMIN")
                                // for everything else, the user has to be authenticated
                                .anyRequest().authenticated()
                )
                // setting custom entry point for unauthenticated request
                // setting custom access denied handler for not authorized request
                .exceptionHandling(handler -> handler
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(unauthorizedHandler))
                // adding the custom filter before UsernamePasswordAuthenticationFilter in the filter chain
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterAfter(rateLimitFilter, JwtAuthorizationFilter.class)
                // setting stateless session, because we choose to implement Rest API
                .sessionManagement(s -> s
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;

        return http.build();
    }

}


