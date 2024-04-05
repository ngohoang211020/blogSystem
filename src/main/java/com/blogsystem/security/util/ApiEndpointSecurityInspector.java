package com.blogsystem.security.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.blogsystem.security.constants.SecurityConstants.SYSTEM_WHITELIST;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

public class ApiEndpointSecurityInspector {
    public static boolean isUnsecureRequest(@NonNull final HttpServletRequest request) {
        final var requestHttpMethod = HttpMethod.valueOf(request.getMethod());
        var unsecuredApiPaths = getUnsecuredApiPaths(requestHttpMethod);
        unsecuredApiPaths = Optional.ofNullable(unsecuredApiPaths).orElseGet(ArrayList::new);

        return unsecuredApiPaths.stream().anyMatch(apiPath ->
                new AntPathMatcher().match(apiPath, request.getRequestURI()));
    }

    /**
     * Retrieves the list of unsecured API paths based on the provided HTTP method.
     *
     * @param httpMethod The HTTP method for which unsecured paths are to be retrieved.
     * @return A list of unsecured API paths for the specified HTTP method.s
     */
    private static List<String> getUnsecuredApiPaths(@NonNull final HttpMethod httpMethod) {
        if (httpMethod.equals(GET)) {
            return SYSTEM_WHITELIST;
        } else if (httpMethod.equals(POST)) {
            return SYSTEM_WHITELIST;
        }
        return Collections.emptyList();
    }
}
