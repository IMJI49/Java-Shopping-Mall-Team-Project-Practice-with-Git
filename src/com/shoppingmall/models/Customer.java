package com.shoppingmall.models;

public class Customer extends Person {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double rating;
	private String review;


	public Customer(String id, String password, String name, String address, String email, String phoneNumber) {
		super(id, password, name, address, email, phoneNumber);
		getRole();
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	
	@Override
	public String getRole() {
		return "이용자";
	}

	@Override
	public String toString() {
		return String.format("이름 : %s, id %s, 주소 : %s, 전화번호 : %s",name, id, address, phoneNumber);
	}
	
}

	