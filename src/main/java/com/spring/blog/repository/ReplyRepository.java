package com.spring.blog.repository;

import com.spring.blog.dto.ReplyFindByIdDTO;
import com.spring.blog.dto.ReplyInsertDTO;
import com.spring.blog.dto.ReplyUpdateDTO;
import com.spring.blog.entity.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyRepository {

    List<ReplyFindByIdDTO> findAllByBlogId(long blogId);

    // 댓글번호 입력시 특정 댓글 하나만 가져오는 메서드 findByReplyId() 선언 - 이 정보를 토대로 삭제,수정 기능이 다 나와야 한다
    ReplyFindByIdDTO findByReplyId(long replyId);

    // 삭제는 replyId를 이용한다. deleteByReplyId 메서드를 정의해보자
    void deleteByReplyId(long replyId);

    // 삽입구문은 ReplyInsertDTO를 이용해 사용한다. save 메서드 정의
    // ReplyInsertDTO에 내장된 멤버변수인 blogId(몇번글에), replyWriter(누가), replyContent(무슨내용)
    // 들을 쿼리문에 전달해서 INSERT구문을 완성시키기 위함
    void save(ReplyInsertDTO replyInsertDTO);

    // 수정로직은 ReplyUpdateDTO를 이용해 update 메서드를 호출해 처리한다
    // 수정로직은 replyId를 WHERE절에 집어넣고, replyWriter, replyContent의 내용을 업뎃해주고
    // updatedAt 역시 now()로 바꿔준다.
    void update(ReplyUpdateDTO replyUpdateDTO);

}
