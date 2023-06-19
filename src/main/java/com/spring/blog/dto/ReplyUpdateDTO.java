package com.spring.blog.dto;

import com.spring.blog.entity.Reply;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString @Builder
public class ReplyUpdateDTO {
    // 글번호, 댓글쓴이, 댓글내용
    private long replyId;
    private String replyWriter;
    private String replyContent;

    public ReplyUpdateDTO(Reply reply){
        this.replyId = reply.getReplyId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
    }
}
