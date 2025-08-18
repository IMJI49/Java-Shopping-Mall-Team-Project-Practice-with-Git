package com.shoppingmall.service;

import java.util.Scanner;

import com.shoppingmall.models.Manager;
import com.shoppingmall.models.Order;

public class ManagerService {
	/*
	 * order status : confirm, cancel, shipping
	 * product : add, delete, modify
	 * customer : delete, 회원 검색시 :  이름, 배송지, 전화번호, 상세정보에 id 배송 횟수, 구매 금액 추가
	 *추가 구현시에는 리뷰까지만
	 */
	/*
	 * 상품 재고까지 확인
	 * 1~6번 없애고
	 * 
	 * 1. 주문 관리
	 * 		1. 주문 내역 확인
	 * 		2. 주문 confirm
	 * 		3. 주문 취소
	 * 2. 마이페이지 - customer mypage랑 동일 4,5 감추는것으로
	 * 3. 관리 상품관리 (change 재고 관리 : quantyty int값으로 받아서 증가만)  (5번 전체 상품 보기랑 같음)
	 * 		1. 
	 * 4. 사용자 관리 : 
	 * 		1. 전체 회원 조회 (이름, id, 주소, 폰넘버) - 관리자 나오면 안됩니다
	 * 		2. 회원검색 이름으로 검색
	 *		3. 상세정보는 id 검색 : 비밀번호 제외 모든값 나오게 (주문 내역도 포함)
	 *		4. 회원강제 탈퇴 : id로 탈퇴시키고 사유 작성 및 통보
	 *		5. 돌아가기
	 * 6. 로그아웃
	 * 추가 구현 기능 판매된 아이템 보기 (statistic)
	 */
	private Scanner scanner;
	private Manager manager;
	private UserService userService;

	public ManagerService(UserService userService) {
		this.userService = userService;
		scanner = new Scanner(System.in);
	}
	
	// 1. 주문 내역 확인
	public void showAllOrders() {
		System.out.println("=== 전체 주문 내역 ===");
		for (Order order : userService.getOrders().values()) {
			System.out.printf("주문번호: %s | 고객: %s | 상태: %s | 총액: %,d원\n", 
				order.getOrderID(), order.getCustomer().getName(), order.getStatus(), order.getTotalAmount());
		}
	}

	// 2. 주문 확정 (펜딩 -> 확정)
	public void confirmOrder(String orderId) {
		Order order = userService.getOrders().get(orderId);
		if (order == null) {
			System.out.println("존재하지 않는 주문번호입니다."); return;
		}
		try {
			userService.confirmOrder(order.getStatus(), orderId); // 기존 UserService 동작 재사용
			System.out.println("주문이 확정되었습니다.");
		} catch(Exception e) {
			System.out.println("실패: "+e.getMessage());
		}
	}

	// 3. 주문 취소
	public void cancelOrder(String orderId) {
		Order order = userService.getOrders().get(orderId);
		if (order == null) {
			System.out.println("존재하지 않는 주문번호입니다."); return;
		}
		try {
			userService.cancelOrder(order.getStatus(), orderId);
			System.out.println("주문이 취소되었습니다.");
		} catch(Exception e) {
			System.out.println("실패: "+e.getMessage());
		}
	}

	// 4. 배송 시작
	public void startShipping(String orderId) {
		Order order = userService.getOrders().get(orderId);
		if (order == null) {
			System.out.println("존재하지 않는 주문번호입니다."); return;
		}
		try {
			userService.startShipping(order.getStatus(), orderId);
			System.out.println("주문 배송을 시작합니다.");
		} catch(Exception e) {
			System.out.println("실패: "+e.getMessage());
		}
	}

	// 5. 배송 완료
	public void completeDelivery(String orderId) {
		Order order = userService.getOrders().get(orderId);
		if (order == null) {
			System.out.println("존재하지 않는 주문번호입니다."); return;
		}
		try {
			userService.completeDelivery(order.getStatus(), orderId);
			System.out.println("배송이 완료되었습니다.");
		} catch(Exception e) {
			System.out.println("실패: "+e.getMessage());
		}
	}

	public void adminPlaceOrder(String customerId) {
		try {
			userService.placeOrder(customerId);
			System.out.println("관리자에 의해 주문이 접수되었습니다.");
		} catch(Exception e) {
			System.out.println("실패: "+e.getMessage());
		}
	}


	
}

	