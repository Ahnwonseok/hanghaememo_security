package com.sparta.hanghaememo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseMessageDto {
    private String msg;
    private int statusCode;

    public ResponseMessageDto(int num) { //memo를 Entity를 Dto에 감싸서 보낸다.
        String[] success = {"회원가입 성공", "로그인 성공", "게시글 삭제 성공", "게시글 삭제 실패"};
        this.msg = success[num];
        this.statusCode = 200;
    }
}