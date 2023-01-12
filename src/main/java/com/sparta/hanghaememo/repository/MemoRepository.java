package com.sparta.hanghaememo.repository;


import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByModifiedAtDesc();
    Optional<Memo> findByIdAndUserId(Long id, Long userId);
    List<Memo> findAllByUserId(Long username);
    List<Memo> deleteByIdAndUserId(Long id, Long userId);
}