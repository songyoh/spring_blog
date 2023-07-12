package com.spring.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

// 역직렬화(디비 -> 자바객체)가 가능하도록 blog 테이블 구조에 맞춰서 멤버변수를 선언(카멜케이스)
// 실무에서는 대부분의 경우 엔터티 클래스에서는 @Setter를 만들지 않는다고 한다.
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor @Builder //@Builder:빌더패턴 생성자를 사용가능하게 해줌(@AllArgsConstructor생성자가 있어 사용이 가능해짐)
@Entity //@DynamicInsert// null인 필드값에 기본값 지정된 요소를 집어넣어줌.
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long blogId; // 숫자는 왠만하면 long형을 사용한다

    @Column(nullable = false) // not null
    private String writer;

    @Column(nullable = false) // not null
    private String blogTitle;

    @Column(nullable = false) // not null
    private String blogContent;

    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;

    @ColumnDefault("0") // 카운트는 기본값으로 0을 설정해준다.
    private Long blogCount; // Wrapper Long형(참조형 변수)을 사용, 기본형 변수 long은 null을 가질 수 없음

    //@PrePersist(영속성)은 insert 되기 전 실행할 로직을 작성한다.
    @PrePersist
    public void setDefaultValue(){
        this.blogCount = this.blogCount == null ? 0 : this.blogCount;
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    //@PreUpdate는 update 되기 전 실행할 로직을 작성합니다.
    @PreUpdate
    public void setUpdateValue(){
        this.updatedAt = LocalDateTime.now();
    }

}
