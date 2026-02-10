package com.example.day3sms.controller;

import com.example.day3sms.dto.LoginRequestDto;
import com.example.day3sms.dto.RegisterRequestDto;
import com.example.day3sms.dto.TokenResponseDto;
import com.example.day3sms.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service){
        this.service = service;
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody LoginRequestDto dto){
        return service.login(dto);
    }

    @PostMapping("/register")
    public TokenResponseDto register(@Valid @RequestBody RegisterRequestDto dto){
        return service.register(dto);
    }
}
