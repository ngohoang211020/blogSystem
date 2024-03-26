package com.blogsystem.controller.auth;

import com.blogsystem.cache.json.JsonRedisFactory;
import com.blogsystem.common.ResponseBody;
import com.blogsystem.common.response.APIResponse;
import com.blogsystem.dto.auth.*;
import com.blogsystem.pubsub.publisher.EmailPublisher;
import com.blogsystem.service.AuthService;
import com.blogsystem.util.OTPUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;


@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public APIResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var token = authService.login(loginRequest);
        return new APIResponse<>(token, HttpStatus.OK);
    }
}
