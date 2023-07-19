package com.spring.blog.controller;

import com.spring.blog.entity.User;
import com.spring.blog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    private final UsersService usersService;

    @Autowired
    public UserController(UsersService usersService){ // 생성자
        this.usersService = usersService;
    }

    // GET방식으로 로그인창으로 넘어가는 로직 생성
    // /WEB-INF/views/user/login.jsp 로 넘어가게 하기
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "user/login";
    }

    // GET방식으로 회원가입 폼으로 넘어가는 로직 작성
    // /WEB-INF/views/user/signup.jsp 로 넘어가게 하기
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUpForm() {
        return "user/signup";
    }

    // POST방식으로 회원가입 요청 처리하기
    // 주소는 localhost:8080/signup
    // 커맨드 객체로 User Entity를 선언해, 가입정보를 받아 .save()를 호출하기
    // 실행 결과는 리다이렉트로 /login 으로 돌아가게 하기
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(User user) {
        usersService.save(user);
        return "redirect:/login";
    }

}
