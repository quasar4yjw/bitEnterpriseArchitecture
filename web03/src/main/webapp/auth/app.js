$(function(){
  $('.footer').load('../common/footer.html');
  $('#btnLogin').click(function(event){
    //console.log( $('#save').is(':checked') );
    //alert("로그인하랑");
    
    $.post('../json/auth/login.do'
        , {
          uid : $('#uid').val(),
          pwd : $('#pwd').val(),
          save :  $('#save').is(':checked')
        }
        ,
        function(data){
        //console.log(data);
           if (data.status == 'success') {
             location.href = '../product/app.html';
           } else {
             alert('로그인 아이디 또는 암호가 맞지 않습니다.');
             $('#pwd').val('');
           }
       }
        , 'json');
        
  });
	 	
});


	



function loadProductList(pageNo) {
  if (pageNo <= 0) pageNo = currPageNo;
  
  $.getJSON('../json/product/list.do?pageNo=' + pageNo, 
		   function(data){
	     //console.log(data);
	
		    //console.log(data); 
		       setPageNo(data.currPageNo, data.maxPageNo);
		    //var currPageNo = data.currPageNo;
		       var products = data.products;
		   /*  var prevPageNo = currPageNo - 1;
		    if(data.prevPageNo) */
		    
		       require(['text!templates/product-table.html'], function(html){
		         var template = Handlebars.compile(html);
	           $('#listDiv').html( template(data) );
		       });
		       //xml-http-ajax 내부적으로 사용
		      
		  });
}






