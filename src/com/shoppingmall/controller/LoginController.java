package com.shoppingmall.controller;

import java.util.Scanner;
 
import com.shoppingmall.models.Person;
import com.shoppingmall.repository.PersonRepository;
 
public class LoginController {
	private static LoginController instance;   //싱글통으로 만들어서 전역에서 접근
	private Scanner scanner;
	private Person loggedInUser;
	private boolean isLoggedIn;
	 
private LoginController() {
	this.scanner = new Scanner(System.in);
	this.loggedInUser = null;
	this.isLoggedIn = false;
}


public static LoginController getInstance() {
    if (instance == null) {
        instance = new LoginController();
    }
    return instance;
}
 
	public String login() {
		System.out.println("=== 로그인 메뉴 ===");
		
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        
        System.out.print("비밀번호 : ");
        String password = scanner.nextLine().trim();
        
        // 인증
        String role = PersonRepository.authenticate(id, password);
        
        if(role != null) {
        	this.loggedInUser = PersonRepository.findByIdPublic(id);
        	this.isLoggedIn = true;
    		
        	System.out.println("\n ✅ 로그인 성공! " + loggedInUser.getName() + "님 환영합니다!");
    		
    		return role;
    		
        } else {
        	System.out.println("\n ✖️ 로그인 실패 : ID 또는 비밀번호가 올바르지 않습니다.");

        	this.loggedInUser = null;
        	this.isLoggedIn = false;
        	
        	return null;
        }
 	}

	
	public void logout() {
		if (isLoggedIn()) {
			System.out.println(loggedInUser.getName() + "님이 로그아웃되었습니다.");
			 
			this.loggedInUser = null;
			this.isLoggedIn = false;
			
		} else {
			System.out.println("로그인되어 있지 않습니다.");
			
		}
	}

	public boolean isLoggedIn() {
		return isLoggedIn && loggedInUser != null; 
	}
	
	// 로그인한 사람 구하기
	public Person getLoggedInUser() {
		return this.loggedInUser;
	}
	

}
