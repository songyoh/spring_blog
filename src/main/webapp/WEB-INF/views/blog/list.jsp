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
    <!-- ${blogList} -->
    <div class="container">
        <h1 class="text-center">게시물 목록</h1>
        <table class="table table-hover">
            <thead class="text-center">
                <tr>
                    <th>글번호</th>
                    <th>글제목</th>
                    <th>글쓴이</th>
                    <th>게시일</th>
                    <th>수정일</th>
                    <th>조회수</th>
                </tr>
            </thead>
            <tbody class="text-center">
            <!-- 07/13 리팩토링(페이징처리) 하면서 items명 수정됨 ${blogList} -> ${pageInfo.toList()}-->
                <c:forEach var="blog" items="${pageInfo.toList()}"> 
                    <tr>
                        <th>${blog.blogId}</th>
                        <td><a href="/blog/detail/${blog.blogId}">${blog.blogTitle}</a></td>
                        <td>${blog.writer}</td>
                        <td>${blog.publishedAt}</td>
                        <td>${blog.updatedAt}</td>
                        <td>${blog.blogCount}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table><!-- .table table-hover -->
        <div class="row">
            <form action="" method="GET">
                <a href="/blog/insert" class="btn btn-primary">글쓰기</a>
            </form>

            <div class="text-center">
                <!-- 페이징 처리 버튼 자리 -->
                <ul class="pagination justify-content-center">
                    
                    <!-- 이전페이지 버튼 -->
                    <!-- c:if태그는 test 프로퍼티에 참, 거짓을 판단할 수 있는 식을 넣어주면 참인경우만 해당요소를 표시한다. -->
                    <c:if test="${startPageNum !=1}">
                        <li class="page-item"> <!-- 시작페이지가 아닌 구문이면 -->
                        <a class="page-link" href="/blog/list/${startPageNum - 1}">Previous</a>
                        </li>
                    </c:if>

                    <!-- 번호 버튼 부분
                    begin=시작숫자, end=마지막숫자, var= 반복문 내에서 사용할 변수명 -->
                    <c:forEach begin="${startPageNum}"
                               end="${endPageNum}" 
                               var="btnNum">
                        <li class="page-item ${currentPageNum == btnNum ? 'active':' '}">
                            <a class="page-link" href="/blog/list/${btnNum}">${btnNum}</a>
                        </li>
                    </c:forEach>

                    <!-- 다음페이지 버튼 -->
                    <!-- 다음 페이지는, 현재 endPageNum이 전체적으로 봐도 마지막 번호인 경우에 보여주지 않으면 된다. -->
                    <c:if test="${endPageNum != pageInfo.getTotalPages()}">
                        <li class="page-item">
                        <a class="page-link" href="/blog/list/${endPageNum + 1}">Next</a>
                        </li>
                    </c:if>

                </ul>
            </div>
        </div>
    </div><!-- .container -->
</body>
</html>