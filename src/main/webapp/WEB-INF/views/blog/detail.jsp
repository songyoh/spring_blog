<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<style>
    
</style>
</head>
<body>
    <!-- ${blog} -->
    <h1 class="text-center">${blog.blogId}번의 글</h1>
    <div class="container text-center">
        <!-- <h1>${blog.blogId}번의 게시글</h1> -->
        <table class="table">
            <thead>
                <th>
                    글번호
                </th>
                <th>
                    ${blogId}
                </th>
                <th>
                    글제목
                </th>
                <th>
                    ${blog.blogTitle}
                </th>
                <th>
                    작성자
                </th>
                <th>
                    ${blog.writer}
                </th>
                <th>
                    조회수
                </th>
                <th>
                    ${blog.blogCount}
                </th>
            </thead>
        </table><!-- first-table -->
        <table class="table">
            <thead>
                <th>
                    작성일
                </th>
                <th>
                    ${blog.publishedAt}
                </th>
                <th>
                    수정일
                </th>
                <th>
                    ${blog.updatedAt}
                </th>
            </thead>
        </table><!-- second-table -->
        <table class="table">
            <div>
                ${blog.blogContent}
            </div>
        </table>
        <a href="/blog/list"><button class="btn btn-secondary">목록</button></a>
        <form action="/blog/delete" method="POST"> <!-- 번호 입력시 게시물을 삭제해주는 기능 추가 -->
            <input type="hidden" name="blogId" value="${blog.blogId}">
            <input type="submit" class="btn btn-warning" value="삭제하기">
        </form>
        <form action="/blog/updateform" method="POST">
            <input type="hidden" name="blogId" value="${blog.blogId}">
            <input type="submit" class="btn btn-info" value="수정하기">
        </form>
        <table class="container">
            <div id="replies">

            </div>
        </table>
    </div><!-- .container -->
    <script>
        // 글 구성에 필요한 글번호를 자바스크립트 변수에 저장
        let blogId = "${blog.blogId}";

        // blogId를 받아 전체 데이터를 JS내부로 가져오는 함수
        function getAllReplies(blogId){
            let url = `http://localhost:8080/reply/${blogId}/all`;
            fetch(url, {method:'get'}) // get방식으로 위 주소에 요청넣기
                .then((res) => res.json()) // 응답받은 요소중 json만 뽑기
                .then(data => { // 뽑아온 json으로 처리작업하기
                    console.log(data);
                });
        }
        // 함수 호출
        getAllReplies(blogId);
    </script>
</body>
</html>