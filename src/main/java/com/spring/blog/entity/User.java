package com.spring.blog.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity @Getter @NoArgsConstructor @Setter // 엔터티를 DTO처럼 쓰는 행위(@Setter)는 좋지 못하다
@Table(name = "users") // 만약 클래스명과 테이블명이 다르게 매칭되기를 원하면 사용하는 어노테이션, USER는 Mysql의 예약어.
public class User implements UserDetails { //UserDetails의 구현체만 스프링 시큐리티에서 인증정보로 사용할 수 있음

    // 회원가입 필드로 사용할 멤버변수 선언
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false) // 수정 불가 옵션
    private Long id; // null 체크 대비

    @Column(nullable = false, unique = true) // 프라이머리 키는 아니지만 유일 키로 사용
    private String email;

    @Column(nullable = false, unique = true) // 로그인에 사용할 아이디 지정 필수, 중복 아이디 불가
    private String loginId;

    // Oauth2.0 로그인 사용자의 경우, 로그인에 사용한 이메일이 자동으로 닉네임처럼 부여되므로, 다른 닉네임을 쓸 수 있게 하려면 닉네임 필드도 필요
    @Column(unique = true)
    private String nickname;

    // 비밀번호는 null 허용(OAuth2.0을 활용한 소셜로그인은 비밀번호가 없음)
    private String password;

    @Builder
    public User(String email, String loginId, String nickname, String password, String auth){ // 생성자에서 인증정보(auth)를 요구함
        this.email = email;
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
    }

    // 사용자 닉네임 변경 지원 메서드
    public User update(String nickname) {
        this.nickname = nickname;
        return this;
    }

    // 인증에 필요한 핵심 메서드들
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return password;
    } // 비번 리턴

    @Override
    public String getUsername() {
        return loginId;
    } // 로그인에 사용할 아이디 입력(unique 요소만 가능)

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } // 계정 로그인이 유지되어있는지 (만료 안되었는지) 로그인 중이라면 true 리턴

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // 벤 당하지 않았다면 true 리턴

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // 인증이 끝났는지 여부(비번 만료 여부-3개월, 6개월 비번 변경 하도록)

    @Override
    public boolean isEnabled() {
        return true;
    } // 계정 사용 가능 여부

}
