<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
    
<%
HashMap<String,Object> resultMap = new HashMap<>();
resultMap.put("currPageNo", request.getAttribute("currPageNo"));
resultMap.put("maxPageNo", request.getAttribute("maxPageNo"));
resultMap.put("products", request.getAttribute("products"));
%>
<%=new Gson().toJson(resultMap) %>


<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<c:set var="count" value="1"/>
{
  "currPageNo": ${currPageNo},
  "maxPageNo": ${maxPageNo},
  <c:if test="${!empty prevPageNo}">"prevPageNo":${prevPageNo},</c:if>
  <c:if test="${!empty nextPageNo}">"nextPageNo":${nextPageNo},</c:if>
  "products": [
  <c:forEach items="${products}" var="product" >
    <c:if test="${count > 1}">,</c:if>
     {
     "no": ${product.no},
     "name":"${product.name}",
     "quantity":${product.quantity},
     "maker":"${product.maker}" 
     }
     <c:set var="count" value="${count + 1}" />
  </c:forEach>
  ]
} --%>
