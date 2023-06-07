package com.spring.blog.exception;

public class NotFoundBlogIdException extends RuntimeException { //로그에 확인할 목적으로 만듬

    // 생성자에 에러 사유를 전달할 수 있도록 메세지를 적는다. - 블로그게시번호를 잘못 적었을때 발생될 예외처리를 만듬
    public NotFoundBlogIdException(String message){
        super(message);
    }
}
