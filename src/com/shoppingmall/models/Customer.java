package com.shoppingmall.models;

public class Customer extends Person {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Customer(String id, String password, String name, String adress, String email, String phoneNumber) {
		super(id, password, name, adress, email, phoneNumber);
		getRole();
	}

	@Override
	public String getRole() {
		return "이용자";
	}

	@Override
	public String toString() {
		return String.format("이름 : %s, id %s, 주소 : %s, 전화번호 : %s",name, id, adress, phoneNumber);
	}

}

	