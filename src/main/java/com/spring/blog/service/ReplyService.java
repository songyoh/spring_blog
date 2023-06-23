package com.spring.blog.service;

import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;

import java.util.List;

public interface ReplyService { // 비즈니스 로직(메소드)을 담당할 메소드를 "정의"한다.
    // 글 번호 입력시 전체 조회해서 리턴해주는 findByBlogId() 메서드 정의
    List<ReplyResponseDTO> findAllByBlogId(long blogId);

    // 단일 댓글 번호 입력시, 댓글 정보를 리턴해주는 findByReplyId() 메서드 정의
    ReplyResponseDTO findByReplyId(long replyId);

    // 댓글 번호 입력시 삭제되도록 해 주는 deleteByReplyId() 메서드 정의
    void deleteByReplyId(long replyId);

    // insert 용도로 정의한 DTO를 넘겨 save() 메서드 정의
    void save(ReplyCreateRequestDTO replyCreateRequestDTO);

    // update 용도로 정의한 DTO를 넘겨 update() 메서드 정의
    void update(ReplyUpdateRequestDTO replyUpdateRequestDTO);
}
