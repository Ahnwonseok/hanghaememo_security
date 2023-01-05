package com.sparta.hanghaememo.controller;


import com.sparta.hanghaememo.dto.SuccessResponseDto;
import com.sparta.hanghaememo.dto.UserRequestDto;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.service.MemoService;
import com.sparta.hanghaememo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final MemoService memoService;
    private final UserService userService;
    private SuccessResponseDto successResponseDto;

    @PostMapping("/api/auth/signup")
    public SuccessResponseDto signup(@RequestBody @Valid UserRequestDto requestDto) {
        User user = userService.signup(requestDto);
        return new SuccessResponseDto(0);
    }

    @ResponseBody
    @PostMapping("/api/auth/login")
    public SuccessResponseDto login(@RequestBody UserRequestDto loginRequestDto, HttpServletResponse response) {
        User user = userService.login(loginRequestDto, response);
        return new SuccessResponseDto(1);
    }

}