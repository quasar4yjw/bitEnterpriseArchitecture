var currPageNo;
var maxPageNo;

//돔트리(엘리먼트 트리) 준비 완료(HTML 처음부터 끝까지 읽었을 때)
//$(document).ready(function(){});
$(function(){
  $('.header').load('../common/header.html');
  $('.footer').load('../common/footer.html');
  $('.form').load('form.html');
	loadProductList(1);
	
	 /*$('.data-row a').click(function(){
	    alert('okokok @_@');
	  });*/
	/*$('.data-row a').on(function(){
  alert('okokok @_@');
});*/
	$(document).on('click', '.data-row a', function(){
	  //alert('okokok @_@');//앞으로 존재할 태그에 대해서도!
	  //alert($(this).attr('data-no'));
	  loadProduct($(this).attr('data-no'));
	});
	
});


	

/* $.getJSON('../json/product/list.do', function(data){
  //console.log(data); 
	 //currPageNo = data.currPageNo;
	 //setCurrPageNo(data.currPageNo);
	 setPageNo(data.currPageNo, data.maxPageNo);
	 maxPageNo = data.maxPageNo;
	 var products = data.products;
	/*  var prevPageNo = currPageNo - 1;
	 if(data.prevPageNo) */
	 
	 /* for (var i = 0; i < data.products.length; i++) {
	  //console.log(data.products[i]);
    $('<tr>')
			    .append($('<td>').html(products[i].no))
			    .append($('<td>').html(products[i].name))
			    .append($('<td>').html(products[i].quantity))
			    .append($('<td>').html(products[i].maker))
			    .appendTo('#productTable')
	 }	
	 
	 if (currPageNo)
}); */

$('#prevBtn').click(function(event){
if (currPageNo > 1) 
  loadProductList(currPageNo - 1);
});
$('#nextBtn').click(function(event){
	if (currPageNo < maxPageNo) 
	loadProductList(currPageNo + 1);
});






function setPageNo(currPageNo, maxPageNo) {
	 window.currPageNo = currPageNo;
	 window.maxPageNo = maxPageNo;
	 
	 $('#pageNo').html(currPageNo);
	 if (currPageNo <= 1) $('#prevBtn').css('display', 'none');
	 else $('#prevBtn').css('display', '');
	 
	 if (currPageNo >= maxPageNo) $('#nextBtn').css('display', 'none');
	 else $('#nextBtn').css('display', '');
	}



function loadProductList(pageNo) {
$.getJSON('../json/product/list.do?pageNo=' + pageNo, 
		   function(data){
	     //console.log(data);
	
		    //console.log(data); 
		       setPageNo(data.currPageNo, data.maxPageNo);
		    //var currPageNo = data.currPageNo;
		       var products = data.products;
		   /*  var prevPageNo = currPageNo - 1;
		    if(data.prevPageNo) */
		    
		     $('.data-row').remove();
		      for (var i = 0; i < products.length; i++) {
		        $('<tr>').addClass('data-row')
		     //console.log(data.products[i]);
		      
		         .append($('<td>').html(products[i].no))
		         .append(
		        		 $('<td>').append(
		        				 $('<a>').attr('href', '#')
		        				         .attr('data-no', products[i].no)
		        				         .html(products[i].name)))
		         .append($('<td>').html(products[i].quantity))
		         .append($('<td>').html(products[i].maker))
		         .appendTo('#productTable')
		    }
		      
		  });
}






