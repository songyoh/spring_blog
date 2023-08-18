package com.spring.blog.service;

import com.spring.blog.entity.User;
import com.spring.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService { // UserService는 "인증"만 담당하고, 나머지 회원가입 등은 이 서비스객체(UsersService)로 처리함.

    private final UserRepository userRepository;
    // 암호화 객체가 필요함(DB에 비번을 암호화해서 넣어야 하기 때문)
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(UserRepository userRepository,
                        @Lazy BCryptPasswordEncoder bCryptPasswordEncoder){ // @Lazy는 지연주입(좋은 구조는 아님) 순환참조에서 구조가 완성되었을때 지연주입되는 방식
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 폼에서 날려준 정보를 디비에 적재하되, 비번은 암호화(인코딩)를 진행한 구문을 인서트함.
    public void save(User user){
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(); // save가 호출될때 마다 객체생성해서 처리하는 구문(교재)
        User newUser = User.builder()
                .email(user.getEmail())
                .loginId(user.getLoginId())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .build();

        userRepository.save(newUser);
    }

    // id를 집어넣으면, 해당계정 전체 정보를 얻어올 수 있는 메서드 작성
    public User getByCredentials(String loginId){
        return userRepository.findByLoginId(loginId);
    }

    // 회원가입이 여부 확인하기 위해서 조회하는 구문 추가
    public User findById(Long userId) {
        return userRepository.findById(userId).get();
    }

    // 소셜 로그인은 email 기반 로그인이 되므로 이메일로도 조회하는 구문 추가
    public User findByEmail(String email) {
        // UserRepository에 쿼리메서드 형식으로 이메일 조회구문 추가
        return userRepository.findByEmail(email);
    }

}
