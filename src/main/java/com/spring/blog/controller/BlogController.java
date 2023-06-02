package com.spring.blog.controller;

import com.spring.blog.entity.Blog;
import com.spring.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller // 1. 빈 등록(컨테이너에의해 관리를 받는 객체), 2. url매핑 처리 기능을 함께 갖음
            // 다른 어노테이션과 교환해서 쓸 수 없다
@RequestMapping("/blog")
public class BlogController {

    // 컨트롤러 레이어는 서비스레이어를 직접 호출한다
    private BlogService blogService;

    @Autowired // 생성자 주입
    public BlogController(BlogService blogService){
        this.blogService = blogService;
    }

    // /blog/list 주소로 GET방식 접속시
    // 1. 서비스객체를 이용해 게시글 전체 조회
    // 2. 얻어온 게시글 .jsp로 보낼 수 있도록 적재
    // 3. .jsp 에서 볼 수 있도록 출력
    // 해당 파일의 이름은 board/list.jsp
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        List<Blog> blogList = blogService.findAll();
        model.addAttribute("blogList", blogList);
        // /WEB-INF/views/blog/list.jsp
        return  "/blog/list";
    }

}
