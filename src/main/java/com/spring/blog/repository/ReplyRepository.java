package com.spring.blog.repository;

import com.spring.blog.dto.ReplyFindByIdDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyRepository {

    List<ReplyFindByIdDTO> findAllByBlogId(long blogId);

    // 댓글번호 입력시 특정 댓글 하나만 가져오는 메서드 findByReplyId() 선언 - 이 정보를 토대로 삭제,수정 기능이 다 나와야 한다
    ReplyFindByIdDTO findByReplyId(long replyId);



}
