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
        <div class="card">
            <div class="card-body">
                ${blog.blogContent}
            </div>
        </div> <br> <!-- blogContent -->
        <div class="card">
            <div id="replies" class="card-body">
                
            </div>
        </div> <!-- replies -->
    </div><!-- .container -->      
    <div class="container">
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
    <script>
        // 글 구성에 필요한 글번호를 자바스크립트 변수에 저장
        let blogId = "${blog.blogId}";

        // blogId를 받아 전체 데이터를 JS내부로 가져오는 함수
        //function getAllReplies(blogId){
        function getAllReplies(id){
            // <%-- jsp와 js가 모두 ${변수명} 문법을 공유하고, 이 중 .jsp파일에서는
            // ${}의 해석을 jsp식으로 먼저 하기 때문에, 해당 ${}가 백틱`` 내부에서 쓰이는 경우
            // ${} 형식으로 \를 추가로 왼쪽에 붙여 jsp용으로 작성한 것이 아님을 명시해야함. --%>
            let url = `http://localhost:8080/reply/\${id}/all`;
            //let url = `http://localhost:8080/reply/${blogId}/all`;

            let str = ""; // 받아온 json을 표현할 html 코드를 저장할 문자열 str 선언

            fetch(url, {method:'get'}) // get방식으로 위 주소에 요청넣기
                .then((res) => res.json()) // 응답받은 요소중 json만 뽑기
                .then(replies => { // 뽑아온 json으로 처리작업하기
                    console.log(replies);

                    //for(reply of replies){
                        //console.log(reply);
                        //console.log("-------------");
                        //str += `<h3>글쓴이: \${reply.replyWriter},
                            //댓글내용: \${reply.replyContent}</h3>`
                    //}

                    // .map()을 이용해 간결한 반복문 처리
                    replies.map((reply, i) => { // 첫 파라미터(reply) : 반복대상자료, 두번째 파라미터(i) : 순번
                        str += `\${i+1}번째 댓글 || 글쓴이: \${reply.replyWriter},
                            댓글내용: \${reply.replyContent}<br>`
                    });
              
                    console.log(str); // 저장된 태그 확인
                    // #replies 요소를 변수에 저장해보기
                    const $replies = document.getElementById('replies');
                    // 저장된 #replies의 innerHTML에 str을 대입해 실제 화면에 출력되도록
                    $replies.innerHTML = str;

                });
        }
        // 함수 호출
        getAllReplies(blogId);
    </script>
</body>
</html>