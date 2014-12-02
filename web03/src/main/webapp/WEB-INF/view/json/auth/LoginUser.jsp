<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<c:set var="count" value="1"/>
{
  "userId": ${loginUser.userId}",
  "userName": ${sessionScope.loginUser.userName}",
  "email": "${loginUser.email}",
  "photo": "/web03/fileupload/${loginUser.photo}"
}
