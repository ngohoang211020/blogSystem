package com.blogsystem.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kaka")
public class HelloController {
    @GetMapping
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public String hello(){
        return "helloa";
    }
}
