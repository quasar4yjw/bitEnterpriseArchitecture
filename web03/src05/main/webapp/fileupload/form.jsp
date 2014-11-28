<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%--
바이너리 데이터를 서버에 보내려면
form의 enctype을 multipart/form-data으로 설정해야 한다.

멀티파트 형식의 요청 프로토콜 예)
---------------------------------
POST /web03/fileupload/upload.jsp HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Content-Length: 877229
Cache-Control: max-age=0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
Origin: http://localhost:8080
User-Agent: Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36
Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryEB7Og9Q54XcJBgFv
Referer: http://localhost:8080/web03/fileupload/form.jsp
Accept-Encoding: gzip,deflate
Accept-Language: ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4
Cookie: JSESSIONID=7C4946E50FF7644C7BB58F43FCC893BB
Request Payload
------WebKitFormBoundaryEB7Og9Q54XcJBgFv
Content-Disposition: form-data; name="name"

11
------WebKitFormBoundaryEB7Og9Q54XcJBgFv
Content-Disposition: form-data; name="age"

22
------WebKitFormBoundaryEB7Og9Q54XcJBgFv
Content-Disposition: form-data; name="photo"; filename="sky-blue-"
Content-Type: application/octet-stream

여기에 바이너리 데이터가 있다!!
------WebKitFormBoundaryEB7Og9Q54XcJBgFv--
 --%>
<form action="upload.jsp" method="post" enctype="multipart/form-data">
이름: <input type="text" name="name"><br>
나이: <input type="text" name="age"><br>
사진: <input type="file" name="photo"><br>
<button>등록</button>

</form>
</body>
</html>