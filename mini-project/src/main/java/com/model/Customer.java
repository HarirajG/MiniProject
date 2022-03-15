package com.model;


public class Customer {
	private int id;
	private long mobileNo;
	private String name,street,city,country;
	
	
	public Customer(long mobileNo, String name, String street, String city) {
		this.mobileNo = mobileNo;
		this.name = name;
		this.street = street;
		this.city = city;
	}


	public Customer(long mobileNo, String name, String street, String city, String country) {
		this.mobileNo = mobileNo;
		this.name = name;
		this.street = street;
		this.city = city;
		this.country = country;
	}


	public Customer() {
	
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public long getMobileNo() {
		return mobileNo;
	}


	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	@Override
	public String toString() {
		return "Customer [id=" + id + ", mobileNo=" + mobileNo + ", name=" + name + ", street=" + street + ", city="
				+ city + ", country=" + country + "]";
	}

}
