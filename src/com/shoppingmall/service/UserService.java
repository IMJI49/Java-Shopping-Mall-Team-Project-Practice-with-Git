package com.shoppingmall.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.shoppingmall.exception.CustomerNotFoundException;
import com.shoppingmall.exception.ValidationException;
import com.shoppingmall.models.CartItem;
import com.shoppingmall.models.Customer;
import com.shoppingmall.models.Item;
import com.shoppingmall.models.Manager;
import com.shoppingmall.models.Order;
import com.shoppingmall.models.Order.Status;
import com.shoppingmall.util.ValidationUtils;



public class UserService {
	/*
	 * 카트담기(50 종목 이상 불가능), 제품 설명보기
	 * 일괄 주문, 부분주문
	 * 서치 : 제고는 확인 불가 
	 * 가격범위, 이름, 카테고리 (페이징 10개씩), 이름 상세정보 description
	 * 등록 날짜 3일이내 = 신상품
	 * 구매 10회이상 제품 bestseller
	 * --
	 * 장바구니 : 조회, 상품추가, 수령변경 (+: 수량 1개 증가, - : 수량 1개 감소, +-10 : 10개 단위 증감,0 : 수량 변경 종료)
	 * 전부삭제(5번 장바구니 비우기), 이름삭제(4번 상품삭제)
	 * 마이페이지
	 * 아이디 조회, 이메일, 전화번호, 주소 비밀번호는 비밀 자리 수 상관없이 *16자로 처리
	 * 개인정보 수정 : 이메일, 전화번호, 주소 (변경 사항 없을 시 다른 키워드 입력하게끔, 요구조건 : 비밀번호 맞출 시 할 수 있게(비밀번호 변경, 회원 탈퇴도 마찬가지).
	 * 
	 */
	
	/*
	 * 비 로그인 시 회원가입 절차
	 * 로그인은 관리자에서 받아오는것으로
	 * UI
	 * customer 로그인
	 * 1. 상품 둘러보기 : searchbycategory 카테고리, newitem신상품, bestseller베스트셀러
	 * 2. 상품검색 : searchbyname 
	 */
	private Scanner scanner;
	private Customer customer;
	private HashMap<String, Item> items;
	private HashMap<String, Customer> customers;
	private HashMap<String, Manager> managers;
	private HashMap<String, Order> orders;
	private HashMap<String, ArrayList<CartItem>> carts;
    private LocalDateTime shippingDate; // 배송 완료 날짜
    private String mallName;
    // 리뷰 관련
    private boolean reviewPromptShown; // 리뷰 창 한 번만 뜨게

	public UserService(String mallName) {
		scanner = new Scanner(System.in);
		this.mallName = mallName;
		items = new HashMap<String, Item>();
		customers = new HashMap<String, Customer>();
		managers = new HashMap<String, Manager>();
		orders = new HashMap<String, Order>();
		carts = new HashMap<String, ArrayList<CartItem>>();
	}
	public void placeOrder(String customerID) throws CustomerNotFoundException {
		Customer customer = customers.get(customerID);
		ArrayList<CartItem> items = carts.get(customerID);
		ValidationUtils.requireNotNullCustomer(customer, customerID);
		int sum = 0;
	
	}
    public void confirmOrder(Status status, String orderID) throws ValidationException {
        ValidationUtils.orderPendingCheck(status, "현재 상태에서는 주문 확정이 불가능합니다.");
        status = Status.CONFIRM;
        orders.get(orderID).setStatus(status);
    }

    public void startShipping(Status status, String orderID) throws ValidationException {
        if (status != Status.CONFIRM) throw new ValidationException("확정된 주문만 배송을 시작할 수 있습니다.");
        status = Status.SHIPPING;
        System.out.printf("📦 %s님 주문의 배송이 시작되었습니다. (주문번호 : %s)\n",customer.getId(), orderID);
    }

    public void completeDelivery(Status status, String orderID) throws ValidationException {
        if (status != Status.SHIPPING) throw new ValidationException("배송 중 상태에서만 배송 완료가 가능합니다.");
        status = Status.DELIVERED;
        shippingDate = LocalDateTime.now();
        System.out.printf("✅ [%s] 주문의 배송이 완료되었습니다./n", orderID);
    }

    public void cancelOrder(Status status, String orderID) throws ValidationException {
        if (status != Status.PENDING) throw new ValidationException("PENDING 상태에서만 취소 가능합니다.");
        status = Status.CANCELLED;
        System.err.printf("⚠ 주문 [%s]가 취소되었습니다./n", orderID);
    }

    // 3일 지난 배송 자동 완료
    public void autoCompleteDeliveryIfOver3Days(Status status, String orderID) {
        if (status == Status.SHIPPING && 
            shippingDate != null && 
            shippingDate.plusDays(3).isBefore(LocalDateTime.now())) {
            status = Status.DELIVERED;
            System.out.printf("📦 주문 [%s]은 발송 3일 경과로 자동 완료 처리되었습니다.\n", orderID);
        }
    }

    // 리뷰 안내 (배송 완료 후 1회만)
    public void promptReview(Status status, String orderID) {
        if (status == Status.DELIVERED && !reviewPromptShown) {
            System.out.println("리뷰를 작성해 주세요. (평점 1~5, 내용 3~500자)");
            reviewPromptShown = true;
        }
    }
}

	