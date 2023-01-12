package com.sparta.hanghaememo.dto;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @Pattern(regexp = "^[0-9a-z]*$",message = "알파벳 소문자와 숫자로 입력하세요")
    @Size(min=4,max=10)
    @NotNull
    private String username;

    @Pattern(regexp = "^[0-9a-zA-Z]*$",message = "알파벳 대소문자와 숫자로 입력하세요")
    @Size(min=8,max=15)
    private String password;
}