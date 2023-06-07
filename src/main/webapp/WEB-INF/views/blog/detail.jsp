<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<style>
    div{
        border: 1px solid black;
    }
</style>
</head>
<body>
    <!-- ${blog} -->
    <div class="container text-center">
        <!-- <h1>${blog.blogId}번의 게시글</h1> -->
        <div class="row first-row">
            <div class="col-1">
                글번호
            </div>
            <div class="col-1">
                ${blogId}
            </div>
            <div class="col-2">
                글제목
            </div>
            <div class="col-4">
                ${blog.blogTitle}
            </div>
            <div class="col-1">
                작성자
            </div>
            <div class="col-1">
                ${blog.writer}
            </div>
            <div class="col-1">
                조회수
            </div>
            <div class="col-1">
                ${blog.blogCount}
            </div>
        </div><!-- row first-row -->
        <div class="row second-row">
            <div class="col-1">
                작성일
            </div>
            <div class="col-5">
                ${blog.publishedAt}
            </div>
            <div class="col-1">
                수정일
            </div>
            <div class="col-5">
                ${blog.updatedAt}
            </div>
        </div><!-- row second-row -->
        <div class="table">
            <div class="text-start">
                본문
            </div>
        </div>
        <div class="table">
            <div>
                ${blog.blogContent}
            </div>
        </div>
        <a href="/blog/list"><button class="btn btn-secondary">목록</button></a>
        <form action="/blog/delete" method="POST"> <!-- 번호 입력시 게시물을 삭제해주는 기능 추가 -->
            <input type="hidden" name="blogId" value="${blog.blogId}">
            <input type="submit" class="btn btn-warning" value="삭제하기">
        </form>
        <form action="/blog/updateform" method="POST">
            <input type="hidden" name="blogId" value="${blog.blogId}">
            <input type="submit" class="btn btn-info" value="수정하기">
        </form>
      </div>
</body>
</html>