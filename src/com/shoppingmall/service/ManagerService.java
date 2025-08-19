package com.shoppingmall.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.shoppingmall.exception.ValidationException;
import com.shoppingmall.models.CartItem;
import com.shoppingmall.models.Customer;
import com.shoppingmall.models.Item;
import com.shoppingmall.models.Manager;
import com.shoppingmall.models.Order;
import com.shoppingmall.models.Order.Status;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.repository.ProductRepository;
import com.shoppingmall.repository.UserRepository;
import com.shoppingmall.util.ValidationUtils;

public class ManagerService extends UserService {
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
	private HashMap<String, Manager> managers; 

	public ManagerService(String mallName) {
		super(mallName);
		this.mallName = mallName;
		items = new HashMap<String, Item>();
		List<Item> itemList = FileManagement.readFromFile(ProductRepository.FILE_NAME);
		for (Item item : itemList) {
			items.put(item.getItemID(), item);
		}
		customers = new HashMap<String, Customer>();
		List<Customer> customerList = FileManagement.readFromFile(UserRepository.FILE_NAME);
		for (Customer customer : customerList) {
			customers.put(customer.getId(), customer);
		}
		managers = new HashMap<String, Manager>();
		orders = new HashMap<String, Order>();
		List<Order> orderList = FileManagement.readFromFile(UserRepository.FILE_NAME);
		for (Order order : orderList) {
			orders.put(order.getOrderID(), order);
		}
		carts = new HashMap<String, ArrayList<CartItem>>();
		review = new HashMap<ArrayList<String>, String>();
	}
	
	public HashMap<String, Manager> getManagers() {
		return managers;
	}

	// 1. 주문 내역 확인
	public void showAllOrders() {
		System.out.println("=== 전체 주문 내역 ===");
		for (Order order : orders.values()) {
			System.out.printf("주문번호: %s | 고객: %s | 상태: %s | 총액: %,d원\n", 
				order.getOrderID(), order.getCustomer().getName(), order.getStatus(), order.getTotalAmount());
		}
	}
	public void confirmOrder(Status status, String orderID) throws ValidationException {
		ValidationUtils.orderPendingCheck(status, "현재 상태에서는 주문 확정이 불가능합니다.");
		status = Status.CONFIRM;
		orders.get(orderID).setStatus(status);
	}
	public void startShipping(Status status, String orderID) throws ValidationException {
		if (status != Status.CONFIRM)
			throw new ValidationException("확정된 주문만 배송을 시작할 수 있습니다.");
		status = Status.SHIPPING;
		System.out.printf("📦 %s님 주문의 배송이 시작되었습니다. (주문번호 : %s)\n", customers.get(orderID).getName(), orderID);
	}
	// 3일 지난 배송 자동 완료
	public void autoCompleteDeliveryIfOver3Days(Status status, String orderID) {
		if (status == Status.SHIPPING && shippingDate != null
				&& shippingDate.plusDays(3).isBefore(LocalDateTime.now())) {
			status = Status.DELIVERED;
			System.out.printf("📦 주문 [%s]은 발송 3일 경과로 자동 완료 처리되었습니다.\n", orderID);
		}
	}

}

	