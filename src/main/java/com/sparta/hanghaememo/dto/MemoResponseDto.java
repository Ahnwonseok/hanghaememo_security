package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Memo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemoResponseDto {
    private String username;
    private String contents;
    private String title;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MemoResponseDto(Memo memo) { //memo를 Entity를 Dto에 감싸서 보낸다.
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.contents = memo.getContents();
        this.username = memo.getUsername();
        this.createdAt = memo.getCreatedAt();
        this.modifiedAt = memo.getModifiedAt();
    }
}