<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
  <c:if test="${not empty memberInfo}">
	<div class="row justify-content-center">
	  <div class="col-lg-8 col-md-10 col-sm-12 m-3 text-end">
		<strong>${memberInfo.name}</strong> (${memberInfo.id})님 안녕하세요.
		<a href="${root}/logout">로그아웃</a><br />
	  </div>
	</div>
  </c:if>
  <c:if test="${empty memberInfo}">
	<script>
	alert("로그인이 필요한 페이지입니다.");
	location.href = "${root}/";
	</script>
  </c:if>