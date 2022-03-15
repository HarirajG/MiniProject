<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<title>All Customer Details</title>
</head>
<body>
	<table id="cust_table">
	</table><br><br>
	<form id="cust_form">
		Name:<input type="text" name="name"><br>
		Mobile Number:<input type="tel" name="mobileNo"><br>
		Address:<br>
		<input type="text" name="street" placeholder="street"><br>
		<input type="text" name="city" placeholder="city"><br>
		<input type="submit" value="submit">
	</form>
</body>
<%@include file="/WEB-INF/view/redirect.jspf" %>
<script>
	$(document).ready(function(){
		$cust_table = $("#cust_table");
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8080/customer.do',
			success:function(customers){
				$cust_table.append("<tr>");					
				$cust_table.append("<th>ID</th>");
				$cust_table.append("<th>Name</th>");
				$cust_table.append("<th>Mobile Number</th>");
				$cust_table.append("<th>Address</th>");
				$cust_table.append("</tr>");
				$.each(customers.customer,function(i,customer){
					insertCustomer(customer);
				});
			}
		});
		function insertCustomer(customer){
			$cust_table.append("<tr>");					
			$cust_table.append("<td>"+customer.id+"</td>");
			$cust_table.append("<td>"+customer.name+"</td>");
			$cust_table.append("<td>"+customer["phone number"]+"</td>");
			$cust_table.append("<td>"+customer.address.street+","+customer.address.city+","+customer.address.country+"</td>");
			$cust_table.append("</tr>");
		}
		
		$("#cust_form").submit(function(e){
			e.preventDefault();
			console.log($("#cust_form").serialize());
			$.ajax({
				type: 'POST',
				url: 'http://localhost:8080/customer.do',
				data: $("#cust_form").serialize(),
				success:function(data){
					var data = {"customer_id":data.cust_id};
					$.ajax({
						type: 'GET',
						url: 'http://localhost:8080/customer.do',
						data: data,
						success:function(customers){
							insertCustomer(customers);
						}
					});
					$("#cust_form").trigger("reset");
				}
			});
		});
	});
	

</script>
</html>