package com.spring.blog.exception;

public class NotFoundReplyByReplyIdException extends RuntimeException {

    // 생성자에 에러 사유를 전달할 수 있도록 메세지를 적는다. - 블로그게시번호를 잘못 적었을때 발생될 예외처리를 만듬
    public NotFoundReplyByReplyIdException(String message){
        super(message);
    }
}
