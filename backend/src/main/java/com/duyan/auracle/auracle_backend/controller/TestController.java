package com.duyan.auracle.auracle_backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/public/hello")
    public Map<String, String> publicHello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from public endpoint!");
        response.put("authenticated,", "false");
        return response;
    }

    @GetMapping("/private/hello")
    public Map<String, String> privateHello() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) auth.getPrincipal();

        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from private endpoint!");
        response.put("userId", userId);
        response.put("authenticated", "true");
        return response;
    }
}
