package com.cos.blog.repository;

import com.cos.blog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board, Integer> {
    Optional<Board> deleteAllByUserId(int userId);
}
