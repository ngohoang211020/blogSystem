package com.blogsystem.security.constants;

import java.util.Arrays;
import java.util.List;

public final class SecurityConstants {
    public static final long EXPIRATION = 60 * 60L;
    public static final long EXPIRATION_REMEMBER = 60 * 60 * 24 * 7L;
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    //JWT Constant
    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String FULL_NAME = "fullname";
    public static final String ROLES = "roles";
    public static final String ROLE_IDS = "role_ids";
    public static final String USER_CLAIMS = "user_claims";

    // System WHITELIST
    public static final List<String> SYSTEM_WHITELIST = Arrays.asList(
            "/api/v2/login",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    );
}
