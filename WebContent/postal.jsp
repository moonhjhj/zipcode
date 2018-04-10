<%@page import="kr.co.bit.vo.ZipcodeVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>조회 화면</title>
</head>
<body>
	<!-- 검색(동명) | input text | 검색 버튼  
->DAO에서 받아옴 

-->
	<form action="./command?cmd=searchDong" method="post">
		<input type="text" name="dong"> <input type="submit"
			value="검색">
	</form>
	<br>
	<select>
		<option>주소 선택
		<option>
	</select>
	<br>
	<%
		ArrayList<ZipcodeVO> list = (ArrayList<ZipcodeVO>) request.getAttribute("list");
		if (list == null) {
			list = new ArrayList<ZipcodeVO>();

		}
		System.out.println(list.size());
		for (ZipcodeVO vo : list) {
			out.print(vo.toString() + "<br>");
		}
	%>
</body>
</html>