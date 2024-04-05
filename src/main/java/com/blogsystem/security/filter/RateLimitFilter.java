package com.blogsystem.security.filter;

import com.blogsystem.cache.CacheConstant;
import com.blogsystem.cache.CacheUtil;
import com.blogsystem.common.response.APIResponse;
import com.blogsystem.common.response.APIResponseError;
import com.blogsystem.enums.ServiceErrorDesc;
import com.blogsystem.exception.OverLimitedException;
import com.blogsystem.security.util.ApiEndpointSecurityInspector;
import com.blogsystem.security.util.CurrentUserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {
    private final CacheUtil cacheUtil;
    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final var unsecuredApiBeingInvoked = ApiEndpointSecurityInspector.isUnsecureRequest(request);
        var authentication = CurrentUserUtils.getCurrentUser();
        if (Boolean.FALSE.equals(unsecuredApiBeingInvoked) && authentication != null) {
            var username = authentication.getName();
            var bucket = cacheUtil.getBucket(username);

            if (bucket > 0 ) {
                cacheUtil.saveBucket(username,--bucket);
                final var remainingTokens = CacheConstant.CAPACITY - bucket;
                response.setHeader("X-Rate-Limit-Remaining", String.valueOf(remainingTokens));
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                final var waitPeriod = 5;
                response.setHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitPeriod));
                response.getWriter().write(objectMapper.writeValueAsString(buildErrRespondForOverRateLimitation()));

                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private APIResponseError buildErrRespondForOverRateLimitation(){
        return APIResponseError.builder()
                .code(ServiceErrorDesc.OVER_RATE_LIMIT.getVal())
                .message("Rate limit exceeded, pls wait for a second and try again")
                .timestamp(new Date().getTime())
                .error(ServiceErrorDesc.OVER_RATE_LIMIT.getDesc())
                .build();
    }

    @Scheduled(fixedRateString = "5000")
    public void refillTokens() {
        cacheUtil.refillBucket();
    }

}
