<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
// 쿠키 만료일 지정하기 => 브라우저를 종료하더라도 만료일 기간 동안은 유지한다.
//하드디스크 유지
Cookie cookie1 = new Cookie("name", "aaaa");
cookie1.setMaxAge(60);
cookie1.setPath("/web03/");
Cookie cookie2 = new Cookie("tel", "111-2222");
cookie2.setMaxAge(60);

// 만료일 지정하지 않음 => 브라우저가 실행되는 동안만 유지된다.
//메모리에 유지
Cookie cookie3 = new Cookie("email", "hongDong@test.com");
// 다중 바이트 문자를 보내는 경우(한글 보내기)
// => URL 인코딩 해서 보낸다.
Cookie cookie4 = new Cookie("adress", URLEncoder.encode("강남구", "UTF-8"));

// 쿠키 보내는 방법
// 응답헤더에 포함한다.

/* Set-Cookie:name=aaa
Set-Cookie:tel=111-2222
Set-Cookie:email="hongDong@test.com"; Version=1 */


response.addCookie(cookie1);
response.addCookie(cookie2);
response.addCookie(cookie3);
response.addCookie(cookie4);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>쿠키 보내기</h1>
</body>
</html>