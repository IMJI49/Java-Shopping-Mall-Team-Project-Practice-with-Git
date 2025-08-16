package com.shoppingmall.test;

import com.shoppingmall.controller.ManagerLoginController;
import com.shoppingmall.models.Manager;
import com.shoppingmall.repository.ManagerRepository;

public class ManagerLoginTest {
	
	public static void main(String[] args) {
		System.out.println("=== 관리자 로그인 시스템 테스트 시작 ===\n\n");
        		
        // 테스트
        testManagerLogin();
        testAuthentication();
        testLoginLogout();
        
        System.out.println("\n\n=== 모든 테스트를 완료했습니다.ㅣ ===");
    	
	}

	// 로그인 테스트
	private static void testManagerLogin() {
		System.out.println("==========================================");
		System.out.println("1. 관리자 로그인 테스트");
		System.out.println("==========================================");
		
		
        ManagerLoginController controller = new ManagerLoginController();
        
        // 로그인 전 상태 출력
        System.out.println("\n- 로그인 전의 로그인 상태: " + controller.isLoggedIn());
        
        // requireLogin 테스트 (로그인하지 않은 상태)
        System.out.println("\n- 로그인 필요 기능 테스트 (로그인 전):");

        boolean requireLogin = controller.requireLogin();
        System.out.println(requireLogin);
        
        System.out.println("\n✅ 관리자 로그인 테스트 완료\n");
	}

	// 인증 테스트
	private static void testAuthentication() {
		
		System.out.println("==========================================");
        System.out.println("2. 인증 테스트");
		System.out.println("==========================================");
        
        ManagerRepository repository = new ManagerRepository();
        
        
        
        // 기본 관리자 계정 확인
        Manager testManager = repository.authenticate("manager1", "manager1234");
        if (testManager != null) {
            System.out.println("✅ 기본 관리자 계정 확인(초기화 전): 성공 (강제 초기화를 하지 않아도 초기화가 작동됐음)");
            System.out.println("   - 관리자 이름: " + testManager.getName());
            System.out.println("   - 관리자 ID: " + testManager.getId());
        } else {
            System.out.println("❌ 기본 관리자 계정 확인(초기화 전): 정상 실패");
        }
        
        System.out.println();
        
        // 기본 관리자 계정 확인 (초기화 후)
        repository.initialize();
        
        Manager testManager2 = repository.authenticate("manager1", "manager1234");
        if (testManager2 != null) {
            System.out.println("✅ 기본 관리자 계정 확인(강제 초기화 후): 성공");
            System.out.println("   - 관리자 이름: " + testManager.getName());
            System.out.println("   - 관리자 ID: " + testManager.getId());
        } else {
            System.out.println("❌ 기본 관리자 계정 확인(초기화 후): 실패");
        }
        
        System.out.println();
        
        
        // 틀린 비밀번호 테스트
        Manager invalidManager = repository.authenticate("manager1", "wrongpassword");
        System.out.println("- 틀린 비밀번호 테스트: " + (invalidManager == null ? "✅ 정상 거부" : "❌ 보안 오류"));
        
        System.out.println();
        
        // 존재하지 않는 ID 테스트
        Manager nonexistentManager = repository.authenticate("nonexistent", "password");
        System.out.println("- 존재하지 않는 ID 테스트: " + (nonexistentManager == null ? "✅ 정상 거부" : "❌ 보안 오류"));
        
        System.out.println();
         
//        // 저장된 관리자 수 확인
//        System.out.println("- 저장된 관리자 수: " + repository.getAllManagers().size());
//        
        System.out.println("✅ 인증 시스템 테스트 완료\n");
    }
   
	
	// 로그인, 로그아웃 테스트
	private static void testLoginLogout() {
		// TODO Auto-generated method stub
		System.out.println("==========================================");
        System.out.println("3. 로그인, 로그아웃 테스트");
		System.out.println("==========================================");
        
        ManagerLoginController controller = new ManagerLoginController();
        
        // 초기 상태
        System.out.println("- 초기 로그인 상태: " + controller.isLoggedIn());
        
        // 로그아웃 시도 (로그인하지 않은 상태)
        System.out.println("- 로그인하지 않은 상태에서 로그아웃 시도:");
        controller.logout();
        
        System.out.println("- ✅ 테스트 완료\n");
    }	
	
	
	
	
}