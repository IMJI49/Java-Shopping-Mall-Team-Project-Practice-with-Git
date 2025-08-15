package com.shoppingmall.models;

public class Manager extends Person {



	private static final long serialVersionUID = 1L;

	public Manager(String name, String address, String email, String id, String password, String phoneNumber) {
	    super(id, name, password, address, email, phoneNumber);
	    getRole();

}

	@Override
	public String getRole() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

	