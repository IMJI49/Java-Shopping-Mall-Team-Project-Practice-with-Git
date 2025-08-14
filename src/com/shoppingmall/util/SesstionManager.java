package com.shoppingmall.util;

import com.shoppingmall.models.Customer;
import com.shoppingmall.models.Manager;
import com.shoppingmall.models.Person;

public class SesstionManager {
	/*
	 * costomer 로그인 확인
	 * admin 로그인 확인
	 */
	private static Person currentUser = null;
	private SesstionManager() {
		// 유틸리티 클래스이므로 인스턴스 생성 방지
	}
	public static Customer getCurrentUser() {
		return (Customer) currentUser;
	}
	public static Manager getCurrentManager() {
		return (Manager) currentUser;
	}
	public static void setCurrentUser(Customer currentUser) {
		SesstionManager.currentUser = currentUser;
	}
	public static void setCurrentManager(Person currentUser) {
		SesstionManager.currentUser = currentUser;
	}
	public static boolean isLogIn() {
		return currentUser != null;
	}
}

	