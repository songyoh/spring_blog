package com.spring.blog.config.oauth;

import com.spring.blog.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.util.WebUtils;

public class OAuth2AuthorizationRequestBasedOnCookieRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "ouath2_auth_request";
    private final static int COOKIE_EXPIRE_SECONDS = 18000;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) { // 인증정보 불러오기
        // 토큰을 발급해 쿠키에 실어서 사용자측에 보냈을 때 쿠키(CookieUtil)에서 토큰만 추려내기
        Cookie cookie = WebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        // 얻어온 쿠키(직렬화된 정보이므로)정보를 역직렬화해 자바 내부에서 쓸 수 있게 만들어서 리턴
        return CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) { // 인증정보 저장
        // 저장하려고 했지만 요청정보가 null인 경우(비정상적인 인증요청인 경우)
        if(authorizationRequest == null){
            removeAuthorizationRequest(request, response); // 인증정보 제거 후
            return; // 종료
        }
        // 쿠키에 직렬화된 토큰정보 저장하기
        CookieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
                CookieUtil.serialize(authorizationRequest),
                COOKIE_EXPIRE_SECONDS);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) { // 인증정보 삭제
        return this.loadAuthorizationRequest(request);
    }

    // 실질적인 삭제는 쿠키 삭제로 이뤄짐
    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME); //쿠키 자체를 삭제하는 구문
    }
}
