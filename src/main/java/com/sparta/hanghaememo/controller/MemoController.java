package com.sparta.hanghaememo.controller;


import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.SuccessResponseDto;
import com.sparta.hanghaememo.entity.UserRoleEnum;
//import com.sparta.hanghaememo.service.MemoService;
import com.sparta.hanghaememo.security.UserDetailsImpl;
import com.sparta.hanghaememo.service.MemoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    //게시글 작성
//    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @PostMapping("/api/post")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 응답 보내기
        return memoService.createMemo(requestDto, userDetails.getUser().getUsername());
    }

    //게시글 전체 목록 조회
    @GetMapping("/api/memos")
    public List<MemoResponseDto> getMemoses(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoService.getMemoses(userDetails.getUser());
    }

    //게시글 상세조회
    @GetMapping("/api/memos/{id}")
    public MemoResponseDto getMemos(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        return memoService.getMemos(id, userDetails.getUser());
    }

    //게시글 수정
    @PutMapping("/api/memos/{id}")
    public MemoResponseDto updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoService.update(id, requestDto, userDetails.getUser());
    }

    //게시글 삭제
    @DeleteMapping("/api/memos/{id}")
    public SuccessResponseDto deleteMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoService.deleteMemo(id, userDetails.getUser());
    }
}