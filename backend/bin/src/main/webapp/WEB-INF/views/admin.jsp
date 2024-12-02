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
			<form action="${root}/admin/searchByDateAdmin" method="post">
				<label for="searchDate">날짜 선택:</label> <input type="date" id="searchDate"
					name="searchDate" > 
		        <label for="resource_id">자원 선택</label>
		        <select id="resource_id" name="resource_id" required>
			    <option value="0" disabled selected>자원을 선택하세요</option>
			    <option value="1">Conference Room A</option>
			    <option value="2">Conference Room B</option>
			    <option value="3">Projector 1</option>
			    <option value="4">Projector 2</option>
		        </select>
				</select> <input type="submit" value="필터링"> <a
					href="${root }/admin/listfull" class="main-button">전체보기</a> <a
					href="${root }/" class="main-button">메인으로</a>
			</form>
		</div>



		<table>
			<thead>
				<tr>
					<th>예약 ID</th>
					<th>사용자 ID</th>
					<th>자원</th>
					<th>시작 시간</th>
					<th>종료 시간</th>
					<th>강제 취소</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty products}">
						<c:forEach var="ob" items="${products }">
							<tr>
								<td>${ob.reservation_id }</td>
								<td>${ob.user_id }</td>
								<td>
								    <c:choose>
								        <c:when test="${ob.resource_id == 1}">
								            Conference Room A
								        </c:when>
								        <c:when test="${ob.resource_id == 2}">
								            Conference Room B
								        </c:when>
								        <c:when test="${ob.resource_id == 3}">
								            Projector 1
								        </c:when>
								        <c:when test="${ob.resource_id == 4}">
								            Projector 2
								        </c:when>
								        <c:otherwise>
								            Unknown Resource
								        </c:otherwise>
								    </c:choose>                            
	                            </td>
								<td>${ob.start_time }</td>
								<td>${ob.end_time }</td>
								<td>
									<form action="${root}/admin/delProduct" method="post">
										<input type="hidden" name="productId" value="${ob.reservation_id}">
										<input
											type="submit" value="취소">
									</form>
								</td>
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
</body>
</html>
