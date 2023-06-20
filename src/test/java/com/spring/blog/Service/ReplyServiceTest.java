package com.spring.blog.Service;

import com.spring.blog.dto.ReplyFindByIdDTO;
import com.spring.blog.dto.ReplyInsertDTO;
import com.spring.blog.dto.ReplyUpdateDTO;
import com.spring.blog.service.ReplyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReplyServiceTest {

    // 서비스 객체 세팅
    @Autowired
    ReplyService replyService;

    // findAllByBlogIdTest()는 RepositoryTest코드 참고해서 작성
    @Test
    @Transactional //testcode
    @DisplayName("2번 글에 연동된 댓글 전체를 가져와서 개수가 4개일 것이라 단언")
    public void findAllByBlogIdTest(){
        long blogId = 2; // given : fixture 저장
        List<ReplyFindByIdDTO> replyList = replyService.findAllByBlogId(blogId); // when : 2번글의 댓글 전부 가져오기
        //System.out.println(replyList);
        //assertThat(replyList.size()).isEqualTo(4); // then : 댓글의 개수는 4개일 것이다.
        assertEquals(4, replyList.size());
    }

    @Test
    @Transactional //testcode
    @DisplayName("댓글번호 5번 자료의 replyWriter는 '개발고수', replyId는 3번")
    public void findByReplyIdTest(){
        long replyId = 5;
        String replyWriter = "개발고수";

        ReplyFindByIdDTO result = replyService.findByReplyId(replyId);

        assertEquals(replyId, result.getReplyId());
        assertEquals(replyWriter, result.getReplyWriter());
    }

    @Test
    @Transactional //testcode
    @DisplayName("3번댓글 삭제시, 2번 포스팅 댓글은 3개이고, 3번으로 재요청시 null")
    public void deleteByReplyIdTest(){
        long replyId = 3;
        long blogId = 2;

        replyService.deleteByReplyId(replyId);

        assertEquals(3,replyService.findAllByBlogId(blogId).size());
        assertNull(replyService.findByReplyId(replyId));
    }

    @Test
    @Transactional //testcode
    @DisplayName("replyWriter, replyContent 자유롭게, blogId는 1번으로 입력시 1번글 연동댓글 1개, 입력된 fixture와 일치하는 멤버변수")
    public void saveTest(){
        long blogId = 1;
        String replyWriter = "두두";
        String replyContent = "스프링공부 열심히하세요";
        ReplyInsertDTO replyInsertDTO = ReplyInsertDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        replyService.save(replyInsertDTO);

        List<ReplyFindByIdDTO> resultList = replyService.findAllByBlogId(blogId); // 전체 댓글 가져오기
        assertEquals(1,resultList.size());

        // resultList의 개수 - 1 이 마지막 인덱스 번호이므로, resultList에서 마지막 인덱스 요소만 가져오기
        ReplyFindByIdDTO result = resultList.get(resultList.size() - 1);

        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
    }

    @Test
    @Transactional //testcode
    @DisplayName("replyId 3번의 replyWriter, replyContent 변경, updatedAt과 publishedAt 변경 되었는지 확인")
    public void updateTest(){
        long replyId = 3;
        String replyWriter = "률류";
        String replyContent = "본문 수정합니다";
//        ReplyUpdateDTO replyUpdateDTO = ReplyUpdateDTO.builder()
//                .replyId(replyId)
//                .replyWriter(replyWriter)
//                .replyContent(replyContent)
//                .build();
        ReplyUpdateDTO replyUpdateDTO = new ReplyUpdateDTO();
        replyUpdateDTO.setReplyId(replyId);
        replyUpdateDTO.setReplyWriter(replyWriter);
        replyUpdateDTO.setReplyContent(replyContent);

        replyService.update(replyUpdateDTO);

        ReplyFindByIdDTO result = replyService.findByReplyId(replyId);
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
        assertTrue(result.getUpdatedAt().isAfter(result.getPublishedAt()));
    }
}
