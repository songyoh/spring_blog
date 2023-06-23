package com.spring.blog.repository;

import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //@Log4j2
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
        List<ReplyResponseDTO> replyList = replyRepository.findAllByBlogId(blogId);
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
        ReplyResponseDTO result = replyRepository.findByReplyId(replyId);
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
        //given : 픽스쳐 세팅한 다음 ReplyCreateRequestDTO 생성 후 멤버변수 초기화
        long blogId = 1;
        String replyWriter = "두두";
        String replyContent = "스프링공부 열심히하세요";
        ReplyCreateRequestDTO replyCreateRequestDTO = ReplyCreateRequestDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        //when : insert 실행
        replyRepository.save(replyCreateRequestDTO);

        //then : blogId번 글의 전체 댓글을 가지고 온 다음 마지막 인덱스 요소만 변수에 저장한 뒤
        //       getter를 이용해 위에서 넣은 fixture와 일치하는지 체크
        List<ReplyResponseDTO> resultList = replyRepository.findAllByBlogId(blogId); // 전체 댓글 가져오기
        // resultList의 개수 - 1 이 마지막 인덱스 번호이므로, resultList에서 마지막 인덱스 요소만 가져오기
        ReplyResponseDTO result = resultList.get(resultList.size() - 1);
        // 단언문 작성
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
    }

    @Test
    @Transactional
    @DisplayName("fixture로 수정할 댓글쓴이, 댓글내용, 3번 replyId를 지정합니다. 수정 후 3번자료를 DB에서 꺼내 fixture비교 및 published_at과 updated_at이 다른지 확인")
//    "fixture로 수정할 댓글쓴이, 댓글내용, 3번 replyId를 지정합니다.
//    수정 후 3번자료를 DB에서 꺼내 fixture비교 및 published_at과 updated_at이 다른지 확인"
    public void updateTest(){
        // given : 2번글의 3번째 댓글을 수정한다
        long replyId = 3;
        String replyWriter = "릴리수정";
        String replyContent = "내용수정 내용수정~~";
        ReplyUpdateRequestDTO replyUpdateRequestDTO = ReplyUpdateRequestDTO.builder()
                .replyId(replyId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        // when
        replyRepository.update(replyUpdateRequestDTO);

        // then
        ReplyResponseDTO result = replyRepository.findByReplyId(replyId);
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
        //System.out.println(result);
        //assertFalse(result.getPublishedAt().equals(result.getUpdatedAt()));
        assertTrue(result.getUpdatedAt().isAfter(result.getPublishedAt())); //updatedAt이 publishedAt보다 이후시점(after)이다 이것이 true일 것(assertTrue)이다라고 단언
    }

    @Test
    @Transactional
    @DisplayName("blogId가 2인 글 삭제시, 삭제한 글의 댓글 전체 조회시 0일 것이다")
    public void deleteByBlogIdTest(){
        // given
        long blogId = 2;
        // when
        replyRepository.deleteByBlogId(blogId);
        // then : blogId번 글 전체 댓글을 얻어와서 길이가 0인지 확인
        List<ReplyResponseDTO> resultList = replyRepository.findAllByBlogId(blogId);
        assertEquals(0, resultList.size());
    }

}
