package com.spring.blog.entity;

import lombok.*;

import java.sql.Date;

// 역직렬화(디비 -> 자바객체)가 가능하도록 blog 테이블 구조에 맞춰서 멤버변수를 선언(카멜케이스)
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor @Builder //@Builder:빌더패턴 생성자를 사용가능하게 해줌(@AllArgsConstructor생성자가 있어 사용이 가능해짐)
public class Blog {
    private long blogId; // 숫자는 왠만하면 long형을 사용한다
    private String writer;
    private String blogTitle;
    private String blogContent;
    private Date publishedAt;
    private Date updatedAt;
    private long blogCount;
}
