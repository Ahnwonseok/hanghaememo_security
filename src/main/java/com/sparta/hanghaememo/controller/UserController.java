package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.LoginRequestDto;
import com.sparta.hanghaememo.dto.SignupRequestDto;
import com.sparta.hanghaememo.dto.ResponseMessageDto;
import com.sparta.hanghaememo.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private ResponseMessageDto responseMessageDto;

    //회원가입
    @PostMapping("/api/auth/signup")
    public ResponseMessageDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return new ResponseMessageDto(0);
    }

    //로그인 url
    @ResponseBody
    @PostMapping("/api/auth/login")
    public ResponseMessageDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return new ResponseMessageDto(1);
    }

    //로그인 페이지
    @GetMapping("/api/auth/login-page")
    public ModelAndView loginPage() { return new ModelAndView("login"); }

}