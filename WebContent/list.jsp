<%@page import="kr.co.bit.vo.ZipcodeVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	ArrayList<ZipcodeVO> list = (ArrayList<ZipcodeVO>)request.getAttribute("list");
	System.out.println(list.size());
	for(ZipcodeVO vo : list){
		out.print(vo.toString()+"<br>");
	}
%>
</body>
</html>