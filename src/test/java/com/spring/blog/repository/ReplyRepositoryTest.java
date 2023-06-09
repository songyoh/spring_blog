package com.spring.blog.repository;

import com.spring.blog.dto.ReplyFindByIdDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReplyRepositoryTest {

    @Autowired //test code에서는 필드 주입을 써도 무방하다
    ReplyRepository replyRepository;

    @Test
    @Transactional
    @DisplayName("2번 글에 연동된 댓글 개수가 4개인지 확인")
    public void findAllByBlogIdTest(){
        // given : 2번 글을 조회하기 위한 fixture 저장
        long blogId = 2;
        // when : findAllByBlogId() 호출 및 결과 자료 저장
        List<ReplyFindByIdDTO> replyList = replyRepository.findAllByBlogId(blogId);
        // then : 2번 글에 연동된 댓글이 4개일것이라고 단언
        //assertEquals(4, replyList.size());
        assertThat(replyList.size()).isEqualTo(4);
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 3번 자료의 댓글은 3번이고, 글쓴이는 '릴리'")
    public void findByReplyIdTest(){
        // given : replyId fixture 3 저장
        long replyId = 3;
        // when
        ReplyFindByIdDTO result = replyRepository.findByReplyId(replyId);
        //then
        assertEquals(3, result.getReplyId());
        assertEquals("릴리", result.getReplyWriter());
    }

}
