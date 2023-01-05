package com.sparta.hanghaememo.dto;

import lombok.Getter;

@Getter
public class MemoRequestDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private String password;
}