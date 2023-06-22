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
    <br>
    <h1 class="text-center">${blog.blogId}번의 글</h1><br>
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
            
        </div> <br> <!-- replies -->
        <div class="row">
            <!-- 비동기 form의 경우는 목적지로 이동하지 않고 페이지 내에서 처리가 되므로
            action을 가지지 않는다. ++제출버튼도 제출기능을 막고 fetch요청만 넣는다. -->
            <div class="col-3">
                <input type="text" class="form-control" id="replyWriter" name="replyWriter">
            </div>
            <div class="col-6">
                <input type="text" class="form-control" id="replyContent" name="replyContent">
            </div>
            <div class="col-3">
                <button class="btn btn-primary" id="replySubmit">댓글쓰기</button>
            </div>
        </div> <br>
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
                        str += 
                            `\${i+1}번째 댓글 || 글쓴이: \${reply.replyWriter},
                            댓글내용: \${reply.replyContent}
                                <span class="deleteReplyBtn" data-replyId="\${reply.replyId}">
                                    [삭제]
                                </span>
                            <br>`;
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

        // 댓글입력 함수 : 해당 함수 실행시 비동기 form에 작성된 글쓴이, 내용으로 댓글 입력 + 댓글 목록 또한 갱신되어야 함
        function insertReply(){
            let url = `http://localhost:8080/reply`;

            // 댓글 입력시 금칙어 설정
            //const banedVoca = ["파이썬"];

            // 요소가 다 채워졌는지 확인
            if(document.getElementById("replyWriter").value.trim() === ""){
                alert("글쓴이입력이 누락되었습니다. 입력하시오.");
                return;
            }
            if(document.getElementById("replyContent").value.trim() === ""){
                alert("본문입력이 누락되었습니다. 입력하시오.");
                return;
            }

            fetch(url, {
                method:'post',
                headers: { // header에는 보내는 데이터(댓글정보)의 자료형에 대해서 기술
                    //json 데이터를 요청과 함께 전달, @RequestBody를 입력받는 로직에 추가
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ // 실질적으로 보낼 요청과 json 정보를 기술함. JSON.stringify()는 자바스크립트의 값을 JSON 문자열로 변환한다.
                    replyWriter: document.getElementById("replyWriter").value,
                    replyContent: document.getElementById("replyContent").value,
                    blogId: "${blog.blogId}",
                }), // insert로직이기 때문에 response에 실제 화면에서 사용할 데이터가 전송되지 않음
             }).then(() => {
                // 댓글 작성 후 폼에 작성되어있던 내용 소거
                document.getElementById("replyWriter").value = "";
                document.getElementById("replyContent").value = "";
                alert("댓글이 등록되었습니다.");

                // 댓글 입력 후 댓글 목록을 갱신하기 위해 getAllReplies 함수 호출 - 성능적으로는 좋지않은 코드(전체를 불러오기 때문)
                getAllReplies(blogId);
             });
        }

        // 제출버튼에 이벤트 연결하기
        $replySubmit = document.getElementById("replySubmit");

        $replySubmit.addEventListener('click', insertReply);

        // 이벤트 객체를 활용해야 이벤트 위임을 구현하기 수월하므로 먼저 html객체부터 가져온다.
        // 모든 댓글을 포함하면서 가장 가까운 영역인 #replies에 설정한다.
        const $replies = document.querySelector("#replies");

        $replies.onclick = (e) => {
            //console.log(e);

            // 클릭한 요소가 #replies의 자식태그인 .deleteReplyBtn인지 검사하기
            // 이벤트객체 .target.matches는 클릭한 요소가 어떤 태그인지 검사해준다.

            //if(e.target.matches('#replies>.deleteReplyBtn')){
                //alert("댓글 삭제");
            //}else{
               // alert("댓글삭제 버튼이 아닙니다.");
            //}

            if(!e.target.matches('#replies>.deleteReplyBtn')){
                return;
            }
            // 클릭이벤트 객체의 e의 target 속성의 dataset 속성 내부에 댓글번호가 있으므로 확인
            console.log(e.target.dataset['replyid']);

            const replyId = e.target.dataset['replyid'];

            if(confirm("정말로 삭제하시겠습니까?")){ //confirm : 예, 아니오로 답할 수 있는 경고창을 띄움.
                // 예 선택시 true, 아니오 선택시 false
                //console.log("replyId번 요소 삭제");
                let url =`http://localhost:8080/reply/\${replyId}`;

                fetch(url,{ method: 'delete'})
                .then(() => {
                    // 요청 넣은 후 실행할 코드를 여기에 적는다.
                    alert('댓글이 삭제되었습니다.');
                    // 댓글 삭제 후 댓글 목록을 갱신하기 위해 getAllReplies 함수 호출 - 성능적으로는 좋지않은 코드(전체를 불러오기 때문)
                    getAllReplies(blogId);
                });
            }
        }


    </script>
</body>
</html>