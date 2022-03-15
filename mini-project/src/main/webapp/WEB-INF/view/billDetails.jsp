<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Bill Details</title>
</head>
<body>
<form id="bill_form" method="post">
	Bill Id :<input type="number" name="bill_id">
	<input type="submit" value="Get Bill">
</form>
<div id="msg"></div>
<div id="bill_con">
	<div id="cust_con">
		Name : <div id="cust_name"></div><br>
		Address : <div id="cust_add"></div><br>
		Mobile No : <div id="cust_phno"></div><br>
	</div>
	<div>
		Bill Generated Time : <div id="bill_time"></div><br>
		<table>
			<thead>
				<tr>
					<th>S.No</th>
					<th>Name</th>
					<th>Price</th>
					<th>No.of.Units</th>
					<th>Tax(%)</th>
					<th>Amount</th>
					<th>Total Amount</th>
					<th>Discounted Amount</th>
				</tr>
			</thead>
			<tbody id="pur_table">
			</tbody>
			<br><br>
			<tfoot>
				<tr>
					<th>Item Count:</th>
					<td id="item_count"></td>
					<th>Bill Amount:</th>
					<td id="bill_amount"></td>
					<th>Discount%:</th>
					<td id="bill_discount"></td>
					<th>Bill Amount(Discounted):</th>
					<td id="bill_discounted_amount"></td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>
</body>
<%@include file="/WEB-INF/view/redirect.jspf" %>
<script>
	$(document).ready(function(){
		$("#bill_con").css("display","none");
		$prod_table = $("#pur_table");
		$("#bill_form").submit(function(e){
			e.preventDefault();
			$.ajax({
				method:"get",
				url:"http://localhost:8080/bill.do",
				data:$("#bill_form").serialize(),
				success:function(bill_data){
					
					if(bill_data.code == 201){
						$("#msg").html("Data Found!!!");
						$("#bill_con").css("display","block");
						
						var bill = bill_data.bill;					
						var customer = bill.customer;
						$("#cust_name").html(customer.name);
						$("#cust_add").html(customer.address.street+","+customer.address.city+","+customer.address.country);
						$("#cust_phno").html(customer["phone number"]);
						
						$prod_table.html("");
						$.each(bill.purchase,function(i,product){
							$prod_table.append("<tr>");
							$prod_table.append("<td>"+(i+1)+"</td>");
							$prod_table.append("<td>"+product.name+"</td>");
							$prod_table.append("<td>"+product.price+"</td>");
							$prod_table.append("<td>"+product["no of units"]+"</td>");
							$prod_table.append("<td>"+product["tax percent"]+"</td>");
							$prod_table.append("<td>"+product.amount+"</td>");
							$prod_table.append("<td>"+product["total amount"]+"</td>");
							$prod_table.append("<td>"+product["discounted amount"]+"</td>");
							$prod_table.append("</tr>");
						});					
						$("#item_count").html(bill["item count"]);
						$("#bill_amount").html(bill.amount);
						$("#bill_discounted_amount").html(bill["discounted amount"]);
						$("#bill_discount").html(bill.discount);
						$("#bill_time").html(bill.time);
					}
					else{
						$("#msg").html("Data Not Found!!!");
						$("#bill_con").css("display","none");
					}
					
					
				}
			});
		});
	});
</script>
</html>