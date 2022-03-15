<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Bill Generation</title>
</head>
<body>
	<form method="post" id="bill_form">
		Customer ID : <input type="number" name="customer_id"><br>
		Product 1 : <br>
		<input type="number" name="product1" placeholder="Product ID">
		<input type="number" name="quantity1" placeholder="Quantity"><br>
		Product 2 : <br>
		<input type="number" name="product2" placeholder="Product ID">
		<input type="number" name="quantity2" placeholder="Quantity"><br>
		Product 3 : <br>
		<input type="number" name="product3" placeholder="Product ID">
		<input type="number" name="quantity3" placeholder="Quantity"><br>
		<input type="submit" value="generate bill">
	</form>
	<div id="bill_id"></div>
</body>
<%@include file="/WEB-INF/view/redirect.jspf" %>
<script>
	function getProduct(id,qua){
		p= {};
		$.ajax({
			type:"get",
			url:"http://localhost:8080/product.do",
			async: false,
			data:{
				product_id: id
			},
			success:function(product_data){
				
				if(product_data.code == 404){
					alert("There is no Product with id "+id);
				}
				else{
					if(product_data.product.quantity >= qua){
						p["product_id"]=id;
						//p["product_name"]=product_data.product.name;
						p["product_amount"]=product_data.product.amount;
						p["product_tax"]=product_data.product.tax;
						p["product_price"]=product_data.product.price;
						p["product_quantity"]=qua;
					}
					else{
						alert(product.name+" is out of stock!!!");
					}
				}
			}
		});
		return p;
	}
	
	$(document).ready(function(){
		data1 = [];
		
		$("#bill_form").submit(function(e){
			e.preventDefault();
			
			bool = false;
			var cust_id = $("input[name=customer_id]").val();
			console.log(cust_id);
			$.ajax({
				type:'get',
				url:"http://localhost:8080/customer.do",
				async: false,
				data:{customer_id:cust_id},
				success:function(data){
					console.log(data);
					if(data.code == 200){
						bool = true;
						if(bool){
							p1 = getProduct($("input[name=product1]").val(),$("input[name=quantity1]").val());
							p2 = getProduct($("input[name=product2]").val(),$("input[name=quantity2]").val());
							p3 = getProduct($("input[name=product3]").val(),$("input[name=quantity3]").val());
							
							if(Object.keys(p1).length >0){
								data1.push(p1);
							}
							if(Object.keys(p2).length >0){
								data1.push(p2);
							}
							if(Object.keys(p3).length >0){
								data1.push(p3);
							}
							list = {"products":data1};
							console.log(JSON.stringify(list));
							$.ajax({
								type:"post",
								url:"http://localhost:8080/bill.do",
								data: "customer_id="+cust_id+"&line_items="+JSON.stringify(list),
								success:function(output){
									$("#bill_id").html(output.bill_id);
								},
								failure:function(output){
									console.log(output);
								}
							});
							$("#bill_form").trigger("reset");
						}
						else{
							alert("No valid customer!!!");
						}
					}			
				}
			});
			
		});
	});
</script>
</html>