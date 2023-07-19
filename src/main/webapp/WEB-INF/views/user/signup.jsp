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
        <h2>회원가입</h2>
        <form action="/signup" method="post"> <!-- 어디로 보내는지 목적지 확인! -->
            아이디: <input type="text" name="loginId" required/><br>
            이메일: <input type="text" name="email" required/><br>
            비밀번호: <input type="password" name="password" required/><br>
            <input type="submit" value="회원가입">
        </form>
    </div>
</body>
</html>