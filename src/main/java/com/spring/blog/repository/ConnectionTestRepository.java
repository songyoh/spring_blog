package com.spring.blog.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper // 특징: 빈 컨테이너에 Mybatis 관리 파일로서 적재
public interface ConnectionTestRepository {

    // getNow()는 실행시 호출할 SQL구문은 xml파일 내부에 작성한다.
    String getNow();

}
