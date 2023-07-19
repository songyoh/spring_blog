package com.spring.blog.controller;

import com.spring.blog.entity.Blog;
import com.spring.blog.exception.NotFoundBlogIdException;
import com.spring.blog.service.BlogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller // 1. 빈 등록(컨테이너에의해 관리를 받는 객체), 2. url매핑 처리 기능을 함께 갖음
            // 다른 어노테이션과 교환해서 쓸 수 없다
@RequestMapping("/blog")
@Log4j2 // sout이 아닌 로깅을 통한 디버깅을 위해 선언
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
    // 해당 파일의 이름은 blog/list.jsp

    // localhost:8080/blog/list/5 -예시
    // PathVariable에서 null처리를 할 경우는 아래와 같이 경로패턴변수가 포함된 경로와 없는 경로 두 개를 {}로 묶어준다.
    @RequestMapping({"/list/{pageNum}", "/list"}) //("/list")일 경우 주소장 입력시에는 localhost:8080/blog/list?pageNum={숫자} 입력하면 이동할 수 있다.
    public String list(Model model, @PathVariable(required = false) Long pageNum){ // @PathVariable(required = false) 이렇게 설정시 null값이 들어올 수 있게된다.
//        System.out.println("pageNum으로 받은 번호: " + pageNum);
        //List<Blog> blogList = blogService.findAll();
        Page<Blog> pageInfo = blogService.findAll(pageNum);
//        System.out.println("-------------");
//        System.out.println(blogList.toList());

        // 한 페이지에 보여야 하는 페이징 버튼 그룹의 갯수
        final int PAGE_BTN_NUM = 10;

        // 현재 조회중인 페이지 번호(0부터 세므로 반드시 +1을 해줘야 함)
        int currentPageNum = pageInfo.getNumber() + 1; // 현재 조회중인 페이지에 강조하기 위해서 필요한 구문

        // 현재 조회중인 페이지 그룹의 끝번호
        int endPageNum = (int)Math.ceil(currentPageNum / (double)PAGE_BTN_NUM) * PAGE_BTN_NUM;

        // 현재 조회중인 페이지 그룹의 시작번호
        int startPageNum = (endPageNum - PAGE_BTN_NUM) + 1;

        // 마지막 그룹번호 보정
        endPageNum = endPageNum > pageInfo.getTotalPages() ? pageInfo.getTotalPages() : endPageNum;

        // preb

        model.addAttribute("currentPageNum", currentPageNum);
        model.addAttribute("endPageNum", endPageNum);
        model.addAttribute("startPageNum", startPageNum);
        model.addAttribute("pageInfo", pageInfo);
        // /WEB-INF/views/blog/list.jsp
        return  "/blog/list";
    }

    // 디테일 페이지 주소 패턴
    // /blog/detail/글번호
    // 위 방식으로 글 번호를 입력받아, service를 이용해 해당 글 번호 요소만 얻어서
    // 뷰에 적재하는 코드를 아래쪽에 작성
    @RequestMapping("/detail/{blogId}")
    public String detail(Model model, @PathVariable long blogId, Principal principal) {

        model.addAttribute("username", principal.getName());
        Blog blog = blogService.findById(blogId);

        if(blog == null){
            try {
                throw new NotFoundBlogIdException("없는 blogId를 조회했습니다. 조회번호: " + blogId);
            } catch (NotFoundBlogIdException e) {
                e.printStackTrace(); // 예외 메세지 체크
                return "blog/NotFoundBlogIdExceptionResultPage";
            }
        }
        model.addAttribute("blog",blog);

        //model.addAttribute("blog", blogService.findById(blogId)); 한줄로 간략하게 할 수도 있다

        // /WEB-INF/views/blog/detail.jsp
        return "/blog/detail";
    }

    // 폼 페이지와 실제 등록 url은 같은 url을 쓰도록한다
    // 대신 폼 페이지는 GET방식으로 접속했을때 연결하고
    // 폼에서 작성완료된 내용은 POST방식으로 제출해 저장하도록 만들어준다.
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String insert(Model model, Principal principal){
        // SecurityContext, Principal은 둘다 인증정보를 갖고 있는 객체이다.
        // Principal은 .jsp에서 security태그를 이용해 인증정보를 활용할 수 있다는 장점을 가짐
        // SecurityContext는 내부적으로 사용하고 싶을 때 인증 (Authentication)을 사용하는 것
        //System.out.println(securityContext);
        //System.out.println(principal.getName()); // 현재 로그인한 username을 얻어 올 수 있다.

                                        // principal.getName()은 현재 로그인한 유저의 아이디를 리턴합니다.
        model.addAttribute("username", principal.getName());
        // /WEB-INF/views/blog/blog-form.jsp
        return "blog/blog-form";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(Blog blog){
        //System.out.println(blog);
        // 서비스 객체를 이용해 DB에 저장
        blogService.save(blog);
        // 저장 후 리다이렉트로 list페이지 실행되게하기
        return "redirect:/blog/list";
    }

    // DELETE로직은 삭제후 /blog/list로 리다이렉트하여 자료가 삭제된 것을 확인해야 한다
    // 글 번호만으로 삭제를 진행해야 한다
    // 따라서, detail페이지에서 삭제버튼을 추가하고, 해당 버튼 클릭시 삭제번호가 전달되어
    // 전달받은 번호를 토대로 삭제하도록 로직을 구성해보자
    // 폼에 추가한 삭제버튼 코드와 컨트롤러에 작성한 DELETE 메서드를 제작해보자
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteById(long blogId) {
        //log.info(blogId); // 로그파일에 기록되지 않음
        blogService.deleteById(blogId);
        return "redirect:/blog/list";
    }

    // UPDATE구문은 다른 내역은 다 INSERT와 비슷하지만
    // 한 가지 차이점은, 폼이 이미 기존에 작성된 정보로 채워져 있다는 점이다
    // 이를 구현하기 위해 수정버튼이 눌렸을때, 제일 먼저 해당 글 정보를 획득한 다음
    // 폼 페이지에 model.addAttribute()로 보내줘야 한다
    // 그 다음 수정용 폼 페이지에 해당 자료를 채운 채 연결해주면 된다
    // 이를위해 value= 를 이용하면 미리 원하는 내용으로 폼을 채워둘 수 있다
    @RequestMapping(value ="/updateform", method = RequestMethod.POST)
    public String update(long blogId, Model model){
        // blogId를 이용해서 blog 객체 받아오기
        Blog blog = blogService.findById(blogId);
        // .jsp로 보내기 위해 적재
        model.addAttribute("blog", blog);
        // WEB-INF/views/blog/blog-update-form.jsp
        return "/blog/blog-update-form";
    }

    // /blog/update 주소로 POST요청을 넣으면 글이 수정되도록 한다
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Blog blog){
        // 받아온 blog 엔터티로 글 수정
        blogService.update(blog);
        // redirect는 해당 글번호의 detail페이지로 넘어가게 하고,
        return "redirect:/blog/detail/"+blog.getBlogId();
        // 어려울 경우 list로 넘어가게 해보자
        //return "redirect:/blog/detail/";
    }






}
