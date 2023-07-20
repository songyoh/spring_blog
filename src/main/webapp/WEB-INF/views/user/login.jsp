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
    <div class=".container">
        <form action="/login" method="post">
            <div class="id">
                <!-- 세션기반(기본) 로그인에서는 아이디는 username, 비밀번호는 password로 고정
                 토큰기반에서는 엔터티에서 사용하는 로그인 명칭으로 바꿔준다.-->
                <input type="text" name="loginId" placeholder="아이디">
            </div>
            <div class="pw">
                <input type="password" name="password" placeholder="비밀번호">
            </div>
            <input type="submit" value="로그인">
        </form>
    </div>
</body>
</html>