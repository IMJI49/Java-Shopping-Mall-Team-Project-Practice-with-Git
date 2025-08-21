package com.shoppingmall;

import com.shoppingmall.controller.MainController;
import com.shoppingmall.models.Customer;
import com.shoppingmall.models.Manager;
import com.shoppingmall.repository.PersonRepository;

public class ShoppingMallSystem {

	public static void main(String[] args) {
		MainController mainController = new MainController();
//		managerAndTestUser1Set();
		mainController.start();
	}
	public static void managerAndTestUser1Set() {
		PersonRepository repository = new PersonRepository();
		Manager manager = new Manager("admin", "admin123", "관리자", "서울시 마포구", "jmadmin@gmail.com", "010-1234-5678");
		repository.savePerson(manager);
		Customer customer = new Customer("user01", "user1234", "사용자1", "서울시", "user01@gmail.com", "010-5678-9012");
		repository.savePerson(customer);
	}
}
