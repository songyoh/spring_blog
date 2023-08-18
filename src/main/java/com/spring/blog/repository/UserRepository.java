package com.spring.blog.repository;

import com.spring.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 계정명으로 user의 전체 정보를 얻어오는 쿼리 메서드
    User findByLoginId(String loginId);
    User findByEmail(String email);
}
