package com.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bill {
	private int id;
	private ArrayList<PurchasedProduct> lineItems;
	private Customer customer;
	public LocalDateTime time;
	private double TotalAmount;
	private int itemCount;
	
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<PurchasedProduct> getLineItems() {
		return lineItems;
	}
	public void setLineItems(ArrayList<PurchasedProduct> lineItems) {
		this.lineItems = lineItems;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public double getTotalAmount() {
		return TotalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		TotalAmount = totalAmount;
	}
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
//	@Override
//	public String toString() {
//		return "{ \"id\":" + id + ", \"lineItems\" :" + lineItems + ", \"customer\":" + customer/* + ", \"time\"" + time*/
//				+ ", \"totalAmount\" :" + TotalAmount + ", \"itemCount\" :" + itemCount + "}";
//	}
	@Override
	public String toString() {
		return "Bill [id=" + id + ", lineItems=" + lineItems + ", customer=" + customer + ", TotalAmount=" + TotalAmount
				+ ", itemCount=" + itemCount + "]";
	}
	
	

}
