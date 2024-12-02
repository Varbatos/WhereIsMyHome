<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib
prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>예약하기</title>
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
      input[type="datetime-local"],
      select {
        padding: 10px;
        margin-bottom: 15px;
        border: 1px solid #ddd;
        border-radius: 4px;
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
      a.cancel-button {
        background-color: #d9534f;
        color: white;
        padding: 10px;
        margin: 10px 0px;
        text-decoration: none;
        border-radius: 4px;
        text-align: center;
      }
      a.cancel-button:hover {
        background-color: #c9302c;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h2>자원 예약</h2>
      <form action="${root }/regist" method="post">
        <label for="userId">사용자 ID</label>
        <input type="text" id="user_id" name="user_id" value="${memberInfo.id }" required />

        <label for="resource_id">자원 선택</label>
        <select id="resource_id" name="resource_id" required>
	    <option value="0" disabled selected>자원을 선택하세요</option>
	    <option value="1">Conference Room A</option>
	    <option value="2">Conference Room B</option>
	    <option value="3">Projector 1</option>
	    <option value="4">Projector 2</option>
        </select>

        <label for="start_time">시작 시간</label>
        <input type="datetime-local" id="start_time" name="start_time" required />

        <label for="end_time">종료 시간</label>
        <input type="datetime-local" id="end_time" name="end_time" required />

        <input type="submit" value="예약하기" />
        <a href="${root }/" class="cancel-button">취소하기</a>
      </form>
    </div>
  </body>
</html>
