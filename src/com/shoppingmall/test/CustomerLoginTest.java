package com.shoppingmall.test;

import com.shoppingmall.controller.CustomerLoginController;
import com.shoppingmall.models.Customer;
import com.shoppingmall.repository.UserRepository;

public class CustomerLoginTest {
	
	public static void main(String[] args) {
		System.out.println("=== 고객 로그인 시스템 테스트 시작 ===\n\n");
         		
        // 테스트
        testCustomerLogin();
        testAuthentication();
        testLoginLogout();
        
        System.out.println("\n\n=== 모든 테스트를 완료했습니다.ㅣ ===");
    	
	}

	// 로그인 테스트
	private static void testCustomerLogin() {
		System.out.println("==========================================");
		System.out.println("1. 고객 로그인 테스트");
		System.out.println("==========================================");
		
		
		CustomerLoginController controller = new CustomerLoginController();
        
        // 로그인 전 상태 출력
        System.out.println("\n- 로그인 전의 로그인 상태: " + controller.isLoggedIn());
        
        // requireLogin 테스트 (로그인하지 않은 상태)
        System.out.println("\n- 로그인 필요 기능 테스트 (로그인 전):");

        boolean requireLogin = controller.requireLogin();
        System.out.println(requireLogin);
        
        System.out.println("\n✅ 고객 로그인 테스트 완료\n");
	}

	// 인증 테스트
	private static void testAuthentication() {
		
		System.out.println("==========================================");
        System.out.println("2. 인증 테스트");
		System.out.println("==========================================");
        
		UserRepository repository = new UserRepository();
        
        
        
        // 기본 고객 계정 확인
        Customer testCustomer = UserRepository.authenticate("customer1", "customer1234");
        if (testCustomer != null) {
            System.out.println("✅ 기본 고객 계정 확인(초기화 전): 성공 (강제 초기화를 하지 않아도 초기화가 작동됐음)");
            System.out.println("   - 고객 이름: " + testCustomer.getName());
            System.out.println("   - 고객 ID: " + testCustomer.getId());
        } else {
            System.out.println("❌ 기본 고객 계정 확인(초기화 전): 정상 실패");
        }
        
        System.out.println();
        
        // 기본 관리자 계정 확인 (초기화 후)
        repository.initialize();
        
        Customer testCustomer2 = UserRepository.authenticate("customer1", "customer1234");
        if (testCustomer2 != null) {
            System.out.println("✅ 기본 고객 계정 확인(강제 초기화 후): 성공");
            System.out.println("   - 고객 이름: " + testCustomer2.getName());
            System.out.println("   - 고객 ID: " + testCustomer2.getId());
        } else {
            System.out.println("❌ 기본 고객 계정 확인(초기화 후): 실패");
        }
        
        System.out.println();
        
        
        // 틀린 비밀번호 테스트
        Customer invalidCustomer = UserRepository.authenticate("customer1", "wrongpassword");
        System.out.println("- 틀린 비밀번호 테스트: " + (invalidCustomer == null ? "✅ 정상 거부" : "❌ 보안 오류"));
        
        System.out.println();
        
        // 존재하지 않는 ID 테스트
        Customer nonexistentCustomer = UserRepository.authenticate("nonexistent", "password");
        System.out.println("- 존재하지 않는 ID 테스트: " + (nonexistentCustomer == null ? "✅ 정상 거부" : "❌ 보안 오류"));
        
        System.out.println();
        
        // 저장된 고객 명수 확인
        System.out.println("- 저장된 고객 수: " + repository.getAllCustomers().size());
        
        System.out.println("✅ 인증 시스템 테스트 완료\n");
    }
   
	
	// 로그인, 로그아웃 테스트
	private static void testLoginLogout() {
		// TODO Auto-generated method stub
		System.out.println("==========================================");
        System.out.println("3. 로그인, 로그아웃 테스트");
		System.out.println("==========================================");
        
		CustomerLoginController controller = new CustomerLoginController();
        
        // 초기 상태
        System.out.println("- 초기 로그인 상태: " + controller.isLoggedIn());
        
        // 로그아웃 시도 (로그인하지 않은 상태)
        System.out.println("- 로그인하지 않은 상태에서 로그아웃 시도:");
        controller.logout();
        
        System.out.println("- ✅ 테스트 완료\n");
    }	
	
	
	
	
}