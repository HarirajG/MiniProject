<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<title>All Product Details</title>
</head>
<body>
	<table id="product_table">
		<thead>
			<tr>
				<th>S.NO</th>
				<th>Name</th>
				<th>Price</th>
				<th>Quantity</th>
				<th>Tax(%)</th>
				<th>Amount</th>
			</tr>
		</thead>
		<tbody id="product_body_table"></tbody>
	</table><br><br>
	<form id="product_form" action="post">
		Name : <input type="text" name="name"><br>
		Price : <input type="number" name="price"><br>
		Quantity : <input type="number" name="quantity"><br>
		Tax : <input type="text" name="tax"><br>
		<input type="submit" value="Add Product">
	</form>
</body>
<%@include file="/WEB-INF/view/redirect.jspf" %>
<script>
	function getProductTable(){
		$product_table = $("#product_table");
		$product_table.css("display","block");
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8080/product.do',
			success:function(products){
				console.log(products);
				$body = $("#product_body_table");
				$body.html("");
				$.each(products.products,function(i,product){
					console.log(product.name);
					$body.append("<tr>");
					$body.append("<td>"+(i+1)+"</td>");
					$body.append("<td>"+product.name+"</td>");
					$body.append("<td>"+product.price+"</td>");
					$body.append("<td>"+product.quantity+"</td>");
					$body.append("<td>"+product.tax+"</td>");
					$body.append("<td>"+product.amount+"</td>");
					$body.append("</tr>");
				});
			}
		});
	}
	$(document).ready(function(){
		$("#product_table").css("display","none");
		getProductTable();
		$("#product_form").submit(function(e){
			e.preventDefault();
			$.ajax({
				type:"post",
				url : "http://localhost:8080/product.do",
				data: $("#product_form").serialize(),
				success:function(){
					getProductTable();
					$("#product_form").trigger("reset");
				}
			});
		});

	});
</script>
</html>