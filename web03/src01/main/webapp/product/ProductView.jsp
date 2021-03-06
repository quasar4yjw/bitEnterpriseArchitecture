<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<jsp:include page="/common/Header.jsp"/>
</head>
<body>
<div class='container'>
<h1>제품 정보(v1.1)</h1>

<%-- <jsp:useBean 
scope="request"
type="java63.servlets.test05.domain.Product"
id="product"></jsp:useBean> --%>
<form class='form-horizontal' role='form' action='update.do' method='post'>
 <div class='form-group'>
 <label for='no' class='col-sm-2 control-label'>번호</label>
 <div class='col-sm-10'>
  <input type='text' class='form-control'  readonly
     id='no' name='no' value='${product.no}'>
  </div>
</div>
<div class='form-group'>
  <label for='name' class='col-sm-2 control-label'>제품</label>
 <div class='col-sm-10'>
   <input type='text' class='form-control' 
      id='name' name='name' value='${product.name}'>
  </div>
  </div>
<div class='form-group'>
<label for='qty' class='col-sm-2 control-label'>수량</label>
<div class='col-sm-10'>
   <input type='text' class='form-control' 
     id='qty' name='qty' value='${product.quantity}'>
 </div>
 </div>
<div class='form-group'>
<label for='mkno' class='col-sm-2 control-label'>제조사</label>
<div class='col-sm-10'>
  <input type='text' class='form-control' 
   id='mkno' name='mkno' value='${product.makerNo}'>
 </div>
</div>
<div class='form-group'>
 <div class='col-sm-offset-2 col-sm-10'>
  <button id='btnUpdate' type='submit' class='btn btn-primary'>변경</button>
  <button id='btnDelete' type='button' class='btn btn-primary'>삭제</button>
  <button id='btnCancel' type='button' class='btn btn-primary'>취소</button>
 </div>
 </div>
</form>
</div>
<script src='../js/jquery-1.11.1.js'></script>
<script>
 $('#btnCancel').click(function(){
  history.back();
 });
$('#btnDelete').click(function(){
 if (confirm('삭제하시겠습니까?')){
  location.href = 'delete.do?no=${product.no}';
 }
});
$('#btnUpdate').click(function() {
 if ($('#name').val().length == 0) {
  alert('제품명은 필수 입력 항목입니다.');
  return false;
 }
 if ($('#qty').val().length == 0) {
  alert('수량은 필수 입력 항목입니다.');
  return false;
 }
 if ($('#mkno').val().length == 0) {
  alert('제조사번호는 필수 입력 항목입니다.');
  return false;
 }
});
</script>
<jsp:include page="/common/Footer.jsp"/>
</body>
</html>
