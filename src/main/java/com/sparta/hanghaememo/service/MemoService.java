package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.SuccessResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.entity.UserRoleEnum;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import com.sparta.hanghaememo.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final MemoRepository memoRepository;

    @Transactional
    public MemoResponseDto createMemo(MemoRequestDto requestDto, String user_name) {

        // 회원가입 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(user_name).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Memo memo = memoRepository.saveAndFlush(new Memo(requestDto, user));

        return new MemoResponseDto(memo);
    }

    @Transactional(readOnly = true)
    public List<MemoResponseDto> getMemoses(User user) {

        List<MemoResponseDto> list = new ArrayList<>();

        // 사용자 권한이 USER일 경우
        if (user.getRole() == UserRoleEnum.USER) {
            List<Memo> memoList =  memoRepository.findAllByUserId(user.getId());
            for(Memo memo : memoList){
                list.add(new MemoResponseDto(memo));
            }
            return list;
        } else { // 사용자 권한이 ADMIN 인 경우
            List<Memo> memoList =  memoRepository.findAll();
            for(Memo memo : memoList){
                list.add(new MemoResponseDto(memo));
            }
            return list;
        }
    }

    @Transactional
    public MemoResponseDto getMemos(Long id, User user) {

        if (user.getRole() == UserRoleEnum.USER) {
            Memo memo = memoRepository.findByIdAndUserId(id,user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("메모 또는 권한이 없습니다.")
            );
            return new MemoResponseDto(memo);
        } else { // 사용자 권한이 ADMIN 인 경우
            Memo memo = memoRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("메모가 없습니다.")
            );
            return new MemoResponseDto(memo);
        }

    }

    @Transactional
    public MemoResponseDto update(Long id, MemoRequestDto requestDto, User user) {
        // 사용자 권한이 USER일 경우
        if ( user.getRole() == UserRoleEnum.USER) {
            System.out.println("=======if=======");
            System.out.println(user.getRole());
            Memo memo = memoRepository.findByIdAndUserId(id,user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("게시물이 존재하지 않거나 권한이 없습니다.")
            );
            memo.update(requestDto);
            return new MemoResponseDto(memo);
        } else { // 사용자 권한이 ADMIN 경우
            System.out.println("=======not if=======");
            System.out.println(user.getRole());
            Memo memo = memoRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
            );
            memo.update(requestDto);
            return new MemoResponseDto(memo);
        }

    }

    @Transactional
    public SuccessResponseDto deleteMemo(Long id, User user) {
        // 사용자 권한이 USER일 경우
        if ( user.getRole() == UserRoleEnum.USER) {
            List<Memo> memo = memoRepository.deleteByIdAndUserId(id,user.getId());
            if (memo.isEmpty()) return new SuccessResponseDto(3);
            return new SuccessResponseDto(2);
        } else { // 사용자 권한이 ADMIN 경우
            Memo memo = memoRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
            );
            memoRepository.deleteById(id);
            return new SuccessResponseDto(2);
        }
//        List<Memo> memo = memoRepository.deleteByIdAndUserId(id,user.getId());
//        if (memo.isEmpty()) return new SuccessResponseDto(3);
//        return new SuccessResponseDto(2);
    }

}