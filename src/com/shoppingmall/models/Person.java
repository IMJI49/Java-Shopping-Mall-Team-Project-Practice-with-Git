package com.shoppingmall.models;

import java.io.Serializable;

public abstract class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected String address;
	protected String email;
	protected String id;
	protected String password;
	protected String phoneNumber;
	public abstract String getRole();
	
	public Person(String id, String password, String name, String address, String email, 
			String phoneNumber) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
		getRole();
	}

	public String getEmail() {
		return email;
	}

	public String getId() {
		return id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getPassword() {
		return password;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return String.format("이름 : %s, 아이디 주소 : %s, 비밀번호 : %s", name,address,password);
			
	}
	
}

	