package com.sparta.hanghaememo.repository;


import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    //모든 게시글을 찾아 수정 내림차순으로 정렬
    List<Memo> findAllByOrderByModifiedAtDesc();
    //메모 아이디와 유저아이디로 검색
    Optional<Memo> findByIdAndUserId(Long id, Long userId);
    //해당 유저의 모든 게시글 검색
    List<Memo> findAllByUserId(Long username);
    //메모를 해당 유저와 게시글 아이디에 맞춰 삭제
    List<Memo> deleteByIdAndUserId(Long id, Long userId);
}