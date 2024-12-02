<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib
prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>회원 가입</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 40px;
        padding: 0;
      }
      .container {
        width: 300px;
        margin: auto;
        background-color: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      }
      h2 {
        text-align: center;
        color: #333;
      }
      form {
        display: flex;
        flex-direction: column;
      }
      label {
        margin-bottom: 5px;
        color: #666;
      }
      input[type="text"],
      input[type="email"],
      input[type="password"] {
        padding: 10px;
        margin-bottom: 15px;
        border: 1px solid #ddd;
        border-radius: 4px;
      }
      .role-group {
        display: flex;
        justify-content: space-around;
        margin-bottom: 15px;
      }
      .role-group label {
        margin-bottom: 0;
      }
      input[type="radio"] {
        margin-right: 5px;
      }
      input[type="submit"] {
        background-color: #5cb85c;
        color: white;
        padding: 10px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
      }
      input[type="submit"]:hover {
        background-color: #4cae4c;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h2>회원 가입</h2>
      <form action="${root }/join" method="post">
        <label for="userId">아이디</label>
        <input type="text" id="userId" name="id" required />

        <label for="password">비밀번호</label>
        <input type="password" id="password" name="password" required />

        <label for="userName">이름</label>
        <input type="text" id="userName" name="name" required />

        <label for="email">이메일</label>
        <input type="email" id="email" name="email" required />

        <label for="isAdmin">권한 설정</label>
        <div class="role-group" id="isAdmin">
          <label for="roleUser">
            <input type="radio" id="roleUser" name="role" value="USER" required /> 사용자
          </label>
          <label for="roleAdmin">
            <input type="radio" id="roleAdmin" name="role" value="ADMIN" /> 관리자
          </label>
        </div>

        <input type="submit" value="가입하기" />
      </form>
    </div>
  </body>
</html>
