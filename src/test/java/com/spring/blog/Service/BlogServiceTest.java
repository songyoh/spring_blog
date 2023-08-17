package com.spring.blog.Service;

import com.spring.blog.entity.Blog;
import com.spring.blog.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class BlogServiceTest {

    @Autowired
    BlogService blogService;

    @Test
    @Transactional // 이 test의 결과가 DB커밋을 하지 않는다는 의미 ,DB롤백이 되지 않는다
    public void findAllTest(){
        // given : 업음

        // when : 전체 데이터 가져오기
        List<Blog> blogList = blogService.findAll();

        // then : 길이가 3일 것이다.
        //assertEquals(3,blogList.size()); 아래 코드와 동일함
        assertThat(blogList.size()).isEqualTo(3); // import assertj...
    }

    // findById 테스트 코드 작성
    @Test
    @Transactional
    public void findByIdTest(){
        // given : 조회할 번호인 2번 변수에 저장, 예상되는 글쓴이, 본문정보 저장
        long id = 2;
        String writer = "2번유저";
        String blogTitle = "2번제목";

        // when : DB에서 2번 유저 알아오기
        Blog blog = blogService.findById(id);

        // then : 얻어온 유저의 번호는 2번, 글쓴이는 writer변수, 제목은 blogTitle변수에 든 값일 것이다
        assertEquals(id, blog.getBlogId());
        assertEquals(writer, blog.getWriter());
        assertEquals(blogTitle, blog.getBlogTitle());
    }

    @Test
    @Transactional
    //@Commit // 트랜잭션 적용된 테스트의 결과를 커밋해서 DB에 반영하도록 만듦
    public void deleteByIdTest(){
        // given : 삭제할 번호 지정
        long id = 1;

        // when
        blogService.deleteById(id);

        // then : 삭제되었다면 총 개수 2개, 1번 조회시 null
        assertEquals(2, blogService.findAll().size());
        assertNull(blogService.findById(id));
    }

    // 저장로직에 대해 테스트코드를 builder패턴을 사용해보자
    @Test
    @Transactional
    public void saveTest(){
        // given : Blog객체에 필요데이터인 writer, Title, Content를 주입해 builder 패턴으로 생성
        String writer = "김자바";
        String blogTitle = "Hello";
        String blogContent = "World!";
        int lastBlogIndex = 3;// 마지막인덱스 번호 조회
        Blog blog = Blog.builder()
                .writer(writer)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build();

        // when : .save()를 호출해 DB에 저장한다.
        blogService.save(blog);

        // then : 전체 요소의 개수가 4개인지 확인하고, 현재 얻어온 마지막 포스팅의 writer, Title, Content가
        // 생성시 사용한 자료와 일치하는지 확인
        assertEquals(4, blogService.findAll().size());
        assertEquals(writer, blogService.findAll().get(lastBlogIndex).getWriter());
        assertEquals(blogTitle, blogService.findAll().get(lastBlogIndex).getBlogTitle());
        assertEquals(blogContent, blogService.findAll().get(lastBlogIndex).getBlogContent());
    }

    @Test
    @Transactional
    public void updateTest(){ // 수정하는 테스트 코드
        // given : 3번글의 제목을 "Bye"로, 본문을 "World!"로 바꾸기 위한 fixture 선언 및 Blog객체 선언
        long blogId = 3;
        String blogTitle = "Bye";
        String blogContent = "World!";

        Blog blog = Blog.builder()
                .blogId(blogId)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build();

        // when : update()메소드를 이용해 상단 blog객체를 파라미터로 수정 실행
        blogService.update(blog);

        // then : blogId번 글을 가져와 blogTitle,blogContent가 수정을 위한 fixture와 동일하다고 단언
        assertEquals(blogId, blogService.findById(blogId).getBlogId());
        assertEquals(blogTitle, blogService.findById(blogId).getBlogTitle());
        assertEquals(blogContent, blogService.findById(blogId).getBlogContent());
    }

    // blog와 함께 reply가 삭제되는 케이스는 따로 다시 테스트코드를 추가로 작성해주는게 좋다

}
