package com.shoppingmall.controller;

import java.util.Scanner;
 
import com.shoppingmall.models.Manager;
import com.shoppingmall.repository.ManagerRepository;

public class ManagerLoginController {
	private ManagerRepository managerRepository;
	private Scanner scanner;
	private Manager manager;
	private boolean isLoggedIn;
	
public ManagerLoginController() {
	this.managerRepository = new ManagerRepository();
	this.scanner = new Scanner(System.in);
	this.manager = null;
	this.isLoggedIn = false;
	
}

	public boolean login() {
		System.out.println("=== 관리자 로그인 메뉴 ===");
		
        System.out.print("관리자 ID: ");
        String id = scanner.nextLine().trim();
        
        System.out.print("비밀번호: ");
        String password = scanner.nextLine().trim();
        
        // 인증
        Manager manager = managerRepository.authenticate(id,password);
        
        if(manager != null) {
        	this.manager = manager;
        	this.isLoggedIn = true;
    		
        	System.out.println("\n ✅ 로그인 성공! 관리자 " + manager.getId() + "님 환영합니다!");
    		
    		return true;
    		
        } else {
        	System.out.println("\n ✖️ 로그인 실패 : ID 또는 비밀번호가 올바르지 않습니다.");

        	this.manager = null;
        	this.isLoggedIn = false;
        	
        	return false;
        }
 	}

	
	public void logout() {
		if (isLoggedIn()) {
			System.out.println("관리자 " + manager.getName() + "님이 로그아웃되었습니다.");
			
			this.manager = null;
			this.isLoggedIn = false;
			
		} else {
			System.out.println("로그인되어 있지 않습니다.");
			
		}
	}

	public boolean isLoggedIn() {
		return this.isLoggedIn && this.manager != null; 
	}

	
	// 로그인 상태 필요(로그인 필요한 기능을 사용하는 경우)
	public boolean requireLogin() {
		if (manager == null) {
			System.out.println("이 기능을 사용하려면 먼저 로그인해주세요.");
			return false;
		}
		return true;
	}
	
}
