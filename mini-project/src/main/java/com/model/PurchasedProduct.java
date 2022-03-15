package com.model;

public class PurchasedProduct extends Product {
	private double total_amount;

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	@Override
	public String toString() {
		return "PurchasedProduct [total_amount=" + total_amount + ", id=" + id + ", quantity=" + quantity + ", name="
				+ name + ", price=" + price + ", tax=" + tax + ", amount=" + amount + "]";
	}

//	@Override
//	public String toString() {
//		return "{\"total_amount\":" + total_amount + ", \"id\":" + id + ",\"quantity\":" + quantity + ", \"name\":\""
//				+ name + "\",\"price\": "+ price + ", \"tax\" :"+ tax + ", \"amount\":" + amount + "}";
//	}
//	
}

