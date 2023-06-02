package com.spring.blog.service;

import com.spring.blog.entity.Blog;

import java.util.List;

public interface BlogService {

    // 비즈니스 로직(메소드)을 담당할 메소드를 "정의"한다.
    // 전체 블로그 포스팅 조회하는 메소드 findAll()을 선언해보자
    List<Blog> findAll();

    // 단일 포스팅을 조회하는 메소드 findById() 선언
    Blog findById(long blogId);

    // 단일 포스팅을 삭제하는 메소드 deleteById() 선언
    void deleteById(long blogId);

    // 단일 포스팅을 게시하는 메소드 save()
    void save(Blog blog);

    // 특정 포스팅을 수정하는 메소드 update()
    void update(Blog blog);

}
