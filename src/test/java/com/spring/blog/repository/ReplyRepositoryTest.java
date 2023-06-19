package com.spring.blog.repository;

import com.spring.blog.dto.ReplyFindByIdDTO;
import com.spring.blog.dto.ReplyInsertDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Test
    @Transactional
    @DisplayName("2번 글에 연동된 댓글번호 2번을 삭제 후, 2번글에 연동된 데이터 개수가 3개, 2번으로 재조회시 null")
    public void deleteByReplyIdTest(){
        // given : 2번 댓 조회 , 글번호 2번 생성
        long blogId = 2;
        long replyId = 2;

        //when : 2번 댓삭
        replyRepository.deleteByReplyId(replyId);

        // then : 2번글에 연동된 댓글 개수 3개, 2번 댓글 재조회시 null
        assertEquals(3,replyRepository.findAllByBlogId(blogId).size());
        assertNull(replyRepository.findByReplyId(replyId));
    }

    @Test
    @Transactional
    @DisplayName("픽스쳐를 이용해 INSERT후, 전체 데이터를 가져와 마지막인덱스 번호 요소를 얻어와 입력했던 fixture와 비교하면 같도록")
    public void saveTest(){
        //given : 픽스쳐 세팅한 다음 ReplyInsertDTO 생성 후 멤버변수 초기화
        long blogId = 1;
        String replyWriter = "두두";
        String replyContent = "스프링공부 열심히하세요";
        ReplyInsertDTO replyInsertDTO = ReplyInsertDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        //when : insert 실행
        replyRepository.save(replyInsertDTO);

        //then : blogId번 글의 전체 댓글을 가지고 온 다음 마지막 인덱스 요소만 변수에 저장한 뒤
        //       getter를 이용해 위에서 넣은 fixture와 일치하는지 체크
        List<ReplyFindByIdDTO> resultList = replyRepository.findAllByBlogId(blogId); // 전체 댓글 가져오기
        // resultList의 개수 - 1 이 마지막 인덱스 번호이므로, resultList에서 마지막 인덱스 요소만 가져오기
        ReplyFindByIdDTO result = resultList.get(resultList.size() - 1);
        // 단언문 작성
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
    }

    @Test
    @Transactional
    @DisplayName("댓글 수정기능")
    public void updateTest(){

    }

}