package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.SuccessResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final MemoRepository memoRepository;

    @Transactional
    public MemoResponseDto createMemo(MemoRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        System.out.println(token);
        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Tokssnss Eor");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Memo memo = memoRepository.saveAndFlush(new Memo(requestDto, user.getUsername()));

//            Memo memo = memoRepository.findById(user.getId()).orElseThrow(
//                    () -> new IllegalArgumentException("해당 메모가 존재하지 않습니다")
//            );
            return new MemoResponseDto(memo);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<MemoResponseDto> getMemoses(HttpServletRequest request) {

        List<MemoResponseDto> list = new ArrayList<>();
        List<Memo> memoList =  memoRepository.findAllByOrderByModifiedAtDesc();

        for(Memo memo : memoList){
            list.add(new MemoResponseDto(memo));
        }
        return list;
    }

    @Transactional
    public MemoResponseDto getMemos(Long id) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new MemoResponseDto(memo);
    }

    @Transactional
    public MemoResponseDto update(Long id, MemoRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        System.out.println(token);
        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Tok en Error");
            }

            Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가  존재하지 않습니다.")
        );
        memo.update(requestDto);
        return new MemoResponseDto(memo);
        } else {
            return null;
        }
    }

    @Transactional
    public SuccessResponseDto deleteMemo(Long id, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        System.out.println(token);
        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Tok en Error");
            }

            memoRepository.deleteById(id);

            return new SuccessResponseDto(2);
        } else {
            return null;
        }
    }

}