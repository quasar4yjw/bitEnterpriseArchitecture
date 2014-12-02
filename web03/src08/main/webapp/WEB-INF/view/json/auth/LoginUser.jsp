<%@page import="java.util.HashMap"%>
<%@page import="com.google.gson.Gson"%>
<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
<%-- <%
HashMap<String,Object> resultMap = new HashMap<>();
if (session.getAttribute("loginUser") !=null) %>
<%//resultMap.put("status", "success");
//resultMap.put("data", session.getAttribute("loginUser"));
//out.write(new Gson().toJson(session.getAttribute("loginUser")));%>
 <%=new Gson().toJson(session.getAttribute("loginUser"))%>
 <% else {%> "userId": ""
<% }%> --%>
<c:choose>

<c:when test="${!empty loginUser}">
<%=new Gson().toJson(session.getAttribute("loginUser"))%>
</c:when>
<c:otherwise>
 { "userId": ""}
</c:otherwise>
</c:choose>

<%-- <%=new Gson().toJson(session.getAttribute("loginUser"))%>  --%>




<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   

<c:set var="count" value="1"/>
{
  "userId": ${loginUser.userId}",
  "userName": ${sessionScope.loginUser.userName}",
  "email": "${loginUser.email}"
  <c:if test="${!empty loginUser.photo}">
  , "photo": "/web03/fileupload/${loginUser.photo}"
  </c:if>
} --%>
