package com.sparta.hanghaememo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemoRequestDto {
    private String title;
    private String contents;
}