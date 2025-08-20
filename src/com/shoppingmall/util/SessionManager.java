package com.shoppingmall.util;


import com.shoppingmall.models.Person;

public class SessionManager {
	/*
	 * costomer 로그인 확인
	 * admin 로그인 확인
	 */
	private static Person currentUser = null;
	private SessionManager() {
		// 유틸리티 클래스이므로 인스턴스 생성 방지
	}
	public static Person getCurrentUser() {
		return currentUser;
	}
	public static void setCurrentManager(Person currentUser) {
		SessionManager.currentUser = currentUser;
	}
	public static boolean isLogIn() {
		return currentUser != null;
	}
}

	