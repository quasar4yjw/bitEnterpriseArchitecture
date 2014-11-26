<%@page import="java.util.HashMap"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//멀티파트 데이터 분석하기

//1. 임시 파일 관리자(메모리,임시 폴더,FTP 서버) 준비
// => 분석기가 분류한 데이터를 임시 디렉토리에 저장한다.
// => 그리고 각 데이터를 객체(FileItem)로 포장한다.
DiskFileItemFactory factory = new DiskFileItemFactory();

//2. 멀티파트 데이터 분석기 준비
ServletFileUpload upload = new ServletFileUpload(factory);

//3. 데이터 분석
List<FileItem> items = upload.parseRequest(request);
HashMap<String, String> paramMap = new HashMap<>();
//4. 분석 결과 처리
for (FileItem item : items) {
	 if (item.isFormField()){//1) 일반 폼 데이터
  		 paramMap.put(item.getFieldName(), item.getString("UTF-8"));
	 } else {//2) 바이너리 데이터
		   paramMap.put(item.getFieldName(), item.getName());
	 }
	 pageContext.setAttribute("paramMap",paramMap);
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
이름: ${paramMap.name}<br>
나이: ${paramMap.age}<br>
사진: ${paramMap.photo}<br>

</form>
</body>
</html>