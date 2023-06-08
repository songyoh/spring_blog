<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <form action="/blog/update" method="POST">
            <div class="row">
                <div class="col-3">
                    <label for="writer" class="form-label">글쓴이</label>
                    <input type="text" class="form-control" name="writer" id="writer" value="${blog.writer}" placeholder="작성자명" readonly>
                </div>
                <div class="col-3">
                    <label for="title" class="form-label">제목</label>
                    <input type="text" class="form-control" name="blogTitle" id="title" value="${blog.blogTitle}" placeholder="제목">
                </div>
            </div>
            <div class="row">
                <div class="col-6">
                    <label for="content" class="form-label">본문</label>
                    <textarea class="form-control" name="blogContent" id="content" rows="10">${blog.blogContent}</textarea>
                    <!-- textarea는 value속성이 없으므로 태그사이에 글입력하듯이 value값을 입력해준다.  -->
                </div>
            </div>
            <form action="/update" method="POST">
                <input type="hidden" value="${blog.blogId}" name="blogId">
                <input type="submit" class="btn btn-primary" value="수정하기">
            </form>            
        </form>
    </div>
</body>
</html>