package com.spring.blog.config;

import com.spring.blog.service.UserService;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 설정 클래스 상위에 붙이는 어노테이션
public class BasicSecurityConfig { // 베이직 방식 인증을 사용하도록 설정하는 클래스
    // 등록할 시큐리티 서비스 멤버변수로 작성하기
    private final UserDetailsService userService;

    // 생성자
    @Autowired
    public BasicSecurityConfig(UserDetailsService userService){
        this.userService = userService;
    }

    // 정적 파일이나 .jsp파일 등 스프링 시큐리티가 기본적으로 적용되지 않을 영역 설정하기.
    @Bean // @Configuration이 붙은 클래스 내부 메서드가 리턴하는 자료는 자동으로 빈에 등록된다.
    public WebSecurityCustomizer configure(){
        return web -> web.ignoring() // 시큐리티 적용을 안 할 경로 설정
                .requestMatchers("/static/**")
                                                            // 기본 경로는 src/main/java/resources 로 잡히고
                                                            // 추후 설정할 정적자원 저장 경로에 보안을 풀었음.
                .dispatcherTypeMatchers(DispatcherType.FORWARD);
                        // MVC방식에서 view(화면)단 파일을 로딩하는것을 보안범위에서 해제.
    }

    // http 요청에 대한 웹 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(authorizationConfig -> { //원래 괄호 안 변수명은 authorizationManagerRequestMatcherRegistry(자동완성된 명칭을 꼭 사용하지 않아도 된다.)
                    authorizationConfig
                            .requestMatchers("/login", "/signup", "/user")//, "/blog/list/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .formLogin(formLoginConfig -> { // 원래 괄호 안의 변수명은 httpSecurityFormLoginConfigurer (자동완성된 명칭을 꼭 사용하지 않아도 된다.)
                    formLoginConfig
                            .loginPage("/login")
                            .defaultSuccessUrl("/blog/list");
                })
                .logout(logoutConfig -> { // 원래 괄호 안의 변수명은 httpSecurityLogoutConfigurer
                    logoutConfig
                            .logoutSuccessUrl("/login")
                            .invalidateHttpSession(true);
                })
                .csrf(csrfConfig -> { // 원래 괄호 안의 변수명은 httpSecurityCsrfConfigurer
                    csrfConfig.disable();
                }) //.csrf(AbstractHttpConfigurer::disable) 한줄로 줄이기도 가능
                .build();

                /*.authorizeRequests() // 인증, 인가 설정 시작부에 사용하는 메서드
                .requestMatchers("/login", "signup", "/user")
                .permitAll() // 위 경로들은 인증없이 접속 가능
                .anyRequest() // 위에 적힌 경로 말고는
                .authenticated() // 로그인 필수임.
                .and() // 다음 설정으로 넘어가기
                .formLogin() // 로그인 폼으로 로그인 제어
                .loginPage("/login") // 로그인 페이지로 지정할 주소
                .defaultSuccessUrl("/blog/list") // 로그인 하면 처음으로 보여질 페이지
                .and()
                .logout() // 로그아웃 관련 설정
                .logoutSuccessUrl("/login") // 로그아웃 성공했으면 넘어갈 경로
                .invalidateHttpSession(true) // 로그아웃하면 다음 접속시 로그인이 풀려있게 설정
                .and()
                .csrf().disable() // csrf 공격 방지용 토큰을 쓰지 않는 구문
                .build();*/
    }

    // 위의 설정을 따라가는 인증은 어떤 서비스 클래스를 통해서 설정할것인가?
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       UserService userService) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
               .userDetailsService(userService) // userService(로그인만 관여함)에 기술된 내용을 토대로 로그인처리
                .passwordEncoder(bCryptPasswordEncoder) // 비밀번호 암호화 저장 모듈
                .and()
                .build();
    } // 로그인 했는지 여부 확인하는 구문

    // 암호화 모듈 임포트
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
