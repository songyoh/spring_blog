package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository // bean container 내부에서 관리하도록 해야 사용할 수 있다.
public interface BlogJPARepository extends JpaRepository<Blog, Long> {
}
