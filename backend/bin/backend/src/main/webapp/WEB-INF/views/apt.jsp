<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 예약 현황</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 40px;
}

.container {
	width: 800px;
	margin: auto;
	background-color: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

table {
	width: 100%;
	border-collapse: collapse;
}

table, th, td {
	border: 1px solid #ddd;
}

th, td {
	padding: 10px;
	text-align: left;
}

th {
	background-color: #f2f2f2;
}

.filter {
	margin-bottom: 20px;
}

input[type="submit"] {
	background-color: #4CAF50;
	color: white;
	padding: 5px;
	border: none;
	border-radius: 4px;
	font-size: 14px;
	cursor: pointer;
}

input[type="submit"]:hover {
	background-color: #45a049;
}

a.main-button {
	background-color: #5bc0de;
	color: white;
	padding: 5px;
	text-decoration: none;
	font-size: 14px;
	border-radius: 4px;
	display: inline-block;
	margin: 10px 0px;
}

a.main-button:hover {
	background-color: #31b0d5;
}
</style>
</head>
<body>
	<div class="container">
		<h2>관리자 예약 현황</h2>
		<div class="filter">
			<form action="${root}/apt/searchByCode" method="post">
				<label for="dong_code">동코드 입력:</label>
				<input type="text" id="dong_code" name="dong_code" value="1111010100">
				<input type="submit" value="필터링">
					<a href="${root }/admin/listfull" class="main-button">전체보기</a>
					<a href="${root }/" class="main-button">메인으로</a> 
			</form>
			<form action="${root}/apt/searchByLatAndLng" method="post">
				<br>
		        <label for="topLat">topLat</label>
		        <input type="text" id="topLat" name="topLat" value="37.582">
		        <label for="bottomLat">bottomLat</label>
		        <input type="text" id="bottomLat" name="bottomLat" value="37.581">
    		   	<label for="leftLng">leftLng</label>
		        <input type="text" id="leftLng" name="leftLng" value="126.9">
		        <label for="rightLng">rightLng</label>
		        <input type="text" id="rightLng" name="rightLng" value="127">
		        <input type="submit" value="필터링">
					<a href="${root }/" class="main-button">메인으로</a> 
	        </form>
				
			
		</div>



		<table>
			<thead>
				<tr>
					<th>아파트 이름</th>
					<th>층</th>
					<th>평</th>
					<th>가격</th>
					<th>위도</th>
					<th>경도</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty apts}">
						<c:forEach var="ob" items="${apts }">
							<tr>
								<td>${ob.apt_nm }</td>
								<td>${ob.floor }</td>
								<td>${ob.exclu_use_ar }</td>
								<td>${ob.deal_amount }</td>
								<td>${ob.latitude }</td>
								<td>${ob.longitude }</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="6" style="text-align: center;">예약 내역이 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	<script>
	$.ajax({
	    type: "POST",
	    url: "/searchByCode",
	    data: { dong_code: 1111017400 }, // yourCode는 검색할 코드
	    success: function(response) {
	        // response는 JSON 형태로 자동 변환됨
	        console.log(response);
	        // 필요한 처리 수행
	    },
	    error: function(xhr, status, error) {
	        console.error("Error: " + error);
	    }
	});
	</script>
</body>
</html>
