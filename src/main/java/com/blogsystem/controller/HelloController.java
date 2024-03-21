package com.blogsystem.controller;

import com.blogsystem.common.ResponseBody;
import com.blogsystem.security.util.CurrentUserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kaka")
public class HelloController {
    @GetMapping
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseBody<String> hello(){
        var currentUser = CurrentUserUtils.getCurrentUser();
        assert currentUser != null;
        return new ResponseBody<>(HttpStatus.OK, currentUser.getName(),"Authorized successfully");
    }
}
