package com.spring.blog.dto;

import com.spring.blog.entity.Reply;
import lombok.*;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString @Builder
public class ReplyInsertDTO {
    // 글번호, 댓글쓴이, 댓글내용
    private long blogId;
    private String replyWriter;
    private String replyContent;

    // DTO는 Entity객체를 이용해서 생성될 수 있어야 하나
    // 반대는 성립하지 않는다.(Entity는 DTO의 내부 구조를 알 필요가 없다.)
    // 엔터티 클래스를 DTO로 변환해주는 메서드
    public ReplyInsertDTO(Reply reply){
        this.blogId = reply.getBlogId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
    }
}
