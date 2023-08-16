package com.spring.blog.service;

import com.spring.blog.config.jwt.TokenProvider;
import com.spring.blog.entity.User;
import com.spring.blog.repository.RefreshTokenRepository;
import com.spring.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider; // 토큰 제공자 호출
    private final RefreshTokenRepository refreshTokenRepository; // 서비스 레이어는 레포지토리 레이어를 호출한다.
    private final UserRepository userRepository; // 서비스 레이어는 레포지토리 레이어를 호출한다.

    public String createNewAccessToken(String refreshToken){
        // refreshToken도 JWT스펙으로 만들어지기 때문에, TokenProvider에 의한 유효성 검증이 가능하다.
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("Unexpected Token");
        }// 유효하지 않은 토큰이면 예외 발생 후 종료

        // 유효한 토큰이면 어떤 유저의 토큰인지 먼저 확인
        Long userId = refreshTokenRepository.findByRefreshToken(refreshToken).getUserId();

        User user = userRepository.findById(userId).get();

        // refreshToken이 유효하며, 유저가 존재한다면 새로운 억세스 토큰을 발급한다.
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
