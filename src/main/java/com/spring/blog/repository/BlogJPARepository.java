package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository // bean container 내부에서 관리하도록 해야 사용할 수 있다.
public interface BlogJPARepository extends JpaRepository<Blog, Long> {

    Page<Blog> findAll(Pageable pageable); // 페이징 정보를 받는 findAll 메서드 오버로딩 정의
}
