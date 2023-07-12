package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import com.spring.blog.repository.BlogJPARepository;
import com.spring.blog.repository.BlogRepository;
import com.spring.blog.repository.ReplyJPARepository;
import com.spring.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // 빈 컨테이너에 적재
public class BlogServiceImpl implements BlogService{

    BlogRepository blogRepository;

    ReplyRepository replyRepository;

    BlogJPARepository blogJPARepository;

    ReplyJPARepository replyJPARepository;

    @Autowired // 생성자 주입이 속도가 더 빠름.
    public BlogServiceImpl(BlogRepository blogRepository,
                           ReplyRepository replyRepository,
                           BlogJPARepository blogJPARepository,
                           ReplyJPARepository replyJPARepository){
        this.blogRepository = blogRepository;
        this.replyRepository = replyRepository;
        this.blogJPARepository = blogJPARepository;
        this.replyJPARepository = replyJPARepository;
    }

    @Override
    public List<Blog> findAll() {
        /*List<Blog> blogList = blogRepository.findAll();
        return blogList;*/
        //return blogRepository.findAll(); <- Mybatis를 활용한 전체 글 가져오기
        return blogJPARepository.findAll(); // <- JPA를 활용한 전체 글 가져오기
    }

    @Override
    public Blog findById(long blogId) {
        //return blogRepository.findById(blogId);
        return blogJPARepository.findById(blogId).get(); // JPA의 findById는 Optional을 리턴하므로, 일반 객체로 만들기 위해 .get()을 사용한다.
                                                //.get()없이 생성할때 optional(null체크를 편히할 수 있게 함) 오류 발생 - 모던 자바 인 액션 참조
        // Optional은 참조형변수에 대해서 null검사 및 처리를 쉽게 할 수 있도록 제공하는 제네릭이다.
        // JPA는 Optional을 쓰는것을 권장하기 위해 리턴 자료형으로 강제해 두었다.
    }

    @Transactional // 둘다 실행되던지 둘 다 실행 안되던지(갑작스런 서버 다운이 되었을 경우를 대비해)
    @Override
    public void deleteById(long blogId) {
        //replyRepository.deleteByBlogId(blogId);
        //blogRepository.deleteById(blogId);
        // 댓글 삭제가 진행되도록
        replyJPARepository.deleteById(blogId);
        blogJPARepository.deleteById(blogId);
    }

    @Override
    public void save(Blog blog) {
        //blogRepository.save(blog);
        blogJPARepository.save(blog);
    }

    @Override
    public void update(Blog blog) {
        // JPA의 수정은, findById()를 이용해 얻어온 엔터티 클래스의 객체 내부 내용물을 수정한 다음
        // 해당 요소를 save() 해서 이뤄진다.
        Blog updatedBlog = blogJPARepository.findById(blog.getBlogId()).get(); // 준영속 상태
        // 파라미터로 들어온 blog 객체를 이용해 수정해준다.
        updatedBlog.setBlogTitle(blog.getBlogTitle()); // 커맨드 객체에 들어온 타이틀로 수정
        updatedBlog.setBlogContent(blog.getBlogContent()); // 커맨드 객체에 들어온 컨텐츠로 수정

        blogJPARepository.save(updatedBlog);
        //blogRepository.update(blog);
    }


}
