package com.shoppingmall.models;

import java.util.Arrays;

public class Customer extends Person {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double rating;
	private String review;
	private int point;
	private boolean[] coupons;

	public Customer(String id, String password, String name, String address, String email, String phoneNumber) {
		super(id, password, name, address, email, phoneNumber);
		point = 0;
		coupons = new boolean[3];
		Arrays.fill(coupons, true);
		getRole();

	}
	public boolean couponUse(String type) {
		switch (type) {
		case "A":
			coupons[0] = false;
			return true;
		case "B":
			coupons[1] = false;
			return true;
		case "C":
			coupons[2] = false;
			return true;
		default:
			System.out.println("쿠폰이 없습니다.");
			return false;
		}
	}
	public void addPoint(Order order) {
		if (order.getStatus() == Order.Status.DELIVERED && order.getCustomer().getId() == getId()) {
			point += (int) (order.getTotalAmount() * 0.05);
		}
	}

	public int getPoint() {
		return point;
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
		return String.format("이름 : %s, id %s, 주소 : %s, 전화번호 : %s", name, id, address, phoneNumber);
	}

}
