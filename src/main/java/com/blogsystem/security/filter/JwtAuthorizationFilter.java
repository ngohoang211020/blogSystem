package com.blogsystem.security.filter;

import com.blogsystem.security.constants.SecurityConstants;
import com.blogsystem.security.service.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String tokenValue = resolveToken(httpServletRequest);
        if (tokenValue == null || tokenValue.isBlank() || !tokenProvider.validateToken(tokenValue)) {
            log.debug("no valid JWT token found, uri: {}", httpServletRequest.getRequestURI());
            // if Authorization header does not exist or token is not valid then skip this filter and continue to execute next filter class
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        var authentication = tokenProvider.getAuthentication(tokenValue);
        var newContext = SecurityContextHolder.createEmptyContext();
        newContext.setAuthentication(authentication);

        // finally, give the authentication token to Spring Security Context
        SecurityContextHolder.setContext(newContext);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return bearerToken.substring(SecurityConstants.TOKEN_PREFIX.length()).trim();
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath()
                .equals("/auth/login");
    }
}
