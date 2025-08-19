package com.shoppingmall.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import com.shoppingmall.exception.CustomerNotFoundException;
import com.shoppingmall.exception.ProductNotFoundException;
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

public class UserService {
	/*
	 * 카트담기(50 종목 이상 불가능), 제품 설명보기 일괄 주문, 부분주문 서치 : 재고는 확인 불가 가격범위, 이름, 카테고리 (페이징
	 * 10개씩), 이름 상세정보 description 등록 날짜 3일이내 = 신상품 구매 10회이상 제품 bestseller -- 장바구니 :
	 * 조회, 상품추가, 수령변경 (+: 수량 1개 증가, - : 수량 1개 감소, +-10 : 10개 단위 증감,0 : 수량 변경 종료)
	 * 전부삭제(5번 장바구니 비우기), 이름삭제(4번 상품삭제) 마이페이지 아이디 조회, 이메일, 전화번호, 주소 비밀번호는 비밀 자리 수
	 * 상관없이 *16자로 처리 개인정보 수정 : 이메일, 전화번호, 주소 (변경 사항 없을 시 다른 키워드 입력하게끔, 요구조건 : 비밀번호
	 * 맞출 시 할 수 있게(비밀번호 변경, 회원 탈퇴도 마찬가지).
	 * 
	 */

	/*
	 * 비 로그인 시 회원가입 절차 로그인은 관리자에서 받아오는것으로 UI customer 로그인 1. 상품 둘러보기 :
	 * searchbycategory 카테고리, newitem신상품, bestseller베스트셀러 2. 상품검색 : searchbyname
	 */
	/*
	 * 개인정보 수정은 3개 address, email, phoneNumber
	 * "바꾸실 수 있는 정보" - typying으로 받는 걸로 String으로 주소, 이메일, 전화 번호
	 * 
	 */
	private Scanner scanner;
	private HashMap<String, Item> items;
	private HashMap<String, Customer> customers;
	private HashMap<String, Manager> managers;
	private HashMap<String, Order> orders;
	private HashMap<String, ArrayList<CartItem>> carts;
	private HashMap<ArrayList<String>, String> review; // ItemID, String Review
	private LocalDateTime shippingDate; // 배송 완료 날짜
	private String mallName;

	public UserService(String mallName) {
		scanner = new Scanner(System.in);
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
		if (status != Status.CONFIRM)
			throw new ValidationException("확정된 주문만 배송을 시작할 수 있습니다.");
		status = Status.SHIPPING;
		System.out.printf("📦 %s님 주문의 배송이 시작되었습니다. (주문번호 : %s)\n", customers.get(orderID).getName(), orderID);
	}

	public void completeDelivery(Status status, String orderID) throws ValidationException {
		if (status != Status.SHIPPING)
			throw new ValidationException("배송 중 상태에서만 배송 완료가 가능합니다.");
		status = Status.DELIVERED;
		shippingDate = LocalDateTime.now();
		System.out.printf("✅ [%s] 주문의 배송이 완료되었습니다./n", orderID);
	}

	public void cancelOrder(Status status, String orderID) throws ValidationException {
		if (status != Status.PENDING)
			throw new ValidationException("PENDING 상태에서만 취소 가능합니다.");
		status = Status.CANCELLED;
		System.err.printf("⚠ 주문 [%s]가 취소되었습니다./n", orderID);
	}

	// 3일 지난 배송 자동 완료
	public void autoCompleteDeliveryIfOver3Days(Status status, String orderID) {
		if (status == Status.SHIPPING && shippingDate != null
				&& shippingDate.plusDays(3).isBefore(LocalDateTime.now())) {
			status = Status.DELIVERED;
			System.out.printf("📦 주문 [%s]은 발송 3일 경과로 자동 완료 처리되었습니다.\n", orderID);
		}
	}

	// 리뷰 안내 (배송 완료 후 1회만)
	public void promptReview(Status status, String orderID) {
		if (status == Status.DELIVERED && !orders.get(orderID).isReviewPromptShown()) {
			System.out.println("리뷰를 작성해 주세요. (평점 1~5, 내용 3~500자)");
			orders.get(orderID).setReviewPromptShown(true);
		}
	}

	public Item getItembyName(String name) {
		for (Item item : items.values()) {
			if (item.getName() == name) {
				return item;
			}
		}
		return null;
	}
	// bestseller 검색
	public void findBestSeller(){
		for (Item item : items.values()) {
			if (item.getSellCount() > 10) {
				System.out.println(item);
			}
		}
	}
	// 카테고리로 상품 검색
	public List<Item> findByCategory(String category) {
		List<Item> items = FileManagement.readFromFile(ProductRepository.FILE_NAME);

		List<Item> foundItems = items.stream().filter(u -> u.getCategory().equals(category)).toList();

		if (foundItems.isEmpty()) {
			return null;
		}

		foundItems.forEach(System.out::println);

		return foundItems;
	}

	// 상품명으로 상품 검색
	public List<Item> findByName(String name) {
		List<Item> items = FileManagement.readFromFile(ProductRepository.FILE_NAME);

		List<Item> foundItems = items.stream().filter(u -> u.getName().equals(name)).toList();

		if (foundItems.isEmpty()) {
			return null;
		}

		foundItems.forEach(System.out::println);

		return foundItems;

	}

	// (1. 1만원 미만, 2. 1-5만원, 3. 5-10만원, 4. 10만원 이상)
	// 가격대로 상품 검색
	public void findByPriceRange(int option) throws ValidationException {
		List<Item> items = FileManagement.readFromFile(ProductRepository.FILE_NAME);

		int minPrice = 0;
		int maxPrice =  Integer.MAX_VALUE;
		
		switch (option) {
			case 1: {
				minPrice = 0;
				maxPrice = 9999;
				break;
			}
			case 2: {
				minPrice = 10000;
				maxPrice = 49999;
				break;
			}
			case 3: {
				minPrice = 50000;
				maxPrice = 99999;
				break;
			}
			case 4: {
				minPrice = 100000;
				maxPrice = Integer.MAX_VALUE;
				break;
			}
			default:
				 throw new ValidationException("잘못된 옵션입니다: " + option);
			}
		
		for (Item item : items) {
			if(item.getPrice() >= minPrice && item.getPrice() <= maxPrice) {
				System.out.println(item);
			}
		}
	}

	// Item 리뷰 추가
	public void addReview(String itemId, String customerId, int rating, String reviewText)
			throws ProductNotFoundException {
		ArrayList<String> ids = new ArrayList<String>();
		ids.add(itemId);
		ids.add(customerId);
		Item item = items.get(itemId);
		ValidationUtils.requireNotNullItem(item, "해당 ID의 상품을 찾을 수 없습니다: ");
		if (item.getReview() == null) {
			System.out.println("리뷰가 없습니다");
		}

		item.getReview().add(reviewText);
		item.addRating(rating);
		items.put(itemId, item);
		List<Item> newItems = new ArrayList<Item>();
		for (Entry<String, Item> entry : items.entrySet()) {
			Item val = entry.getValue();
			newItems.add(val);
		}
		// Item 리스트 다시 저장
		FileManagement.writeToFile(ProductRepository.FILE_NAME, newItems);

		System.out.println("리뷰가 성공적으로 추가되었습니다");
	}

	// 한 상품 상세보기 : 제품 이름, 가격, 설명, 판매 횟수, 리뷰평점, 리뷰들
	public void showItemDetails(String itemname) {
		try {
			Item item = getItembyName(itemname);
			ValidationUtils.requireNotNullItem(item, "해당 이름의 상품을 찾을 수 없습니다");
			System.out.println(item.toString() + String.format("상품 설명 : %s, 판매 횟수 : %d, 리뷰평점 : %.1f", item.getDescription(),item.getSellCount(),item.averageReviewRating()));

			// 추가적으로 리뷰점수 + 리뷰들
				System.out.println("=== " + item.getName() + "의 전체 리뷰 요약 ===");
				System.out.println("평균 평점: " + String.format("%.1f", item.averageReviewRating()));
				System.out.println("총 리뷰 개수: " + item.getReview().size());

				if (!item.getReview().isEmpty()) {
					
					System.out.println("\n리뷰 목록:");
					
					int i = 1;
					
					for (String review : item.getReview()) {
						System.out.println(
								(i) + ". 평점: " + item.getRating().get(i-1) + "/5 | 리뷰: " + review
									+ " | 작성자: ****");
						// 개인정보 노출 위험 최소화를 위해 작성자 비공개 + ****로 항상 표시
					}
				} else {
					System.out.println("아직 리뷰가 없습니다.");
				}
				System.out.println("================================");
			
		} catch (ProductNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		}
	}

	



	
	// 모든 사용자 데이터 반환 (비밀번호 제외)
	public List<Customer> getAllCustomers() {
		List<Customer> customers = FileManagement.readFromFile(UserRepository.FILE_NAME);

		// 비밀번호만 * 16자로 바꾸기
		for (Customer customer : customers) {
			customer.setPassword("*".repeat(16));

		}

		return customers;
	}

	// 전화번호 수정
	public boolean updatePhone(String id, String currentPassword, String newPhone) throws ValidationException {
		ValidationUtils.correctIDPassword(id, currentPassword, customers.get(id));

		List<Customer> customers = FileManagement.readFromFile(UserRepository.FILE_NAME);

		for (Customer customer : customers) {
			if (customer.getId().equals(id)) {
				customer.setPhoneNumber(newPhone);
				FileManagement.writeToFile(UserRepository.FILE_NAME, customers);
				return true;
			}
		}
		return false;
	}

	// 주소 수정
	public boolean updateAddress(String id, String currentPassword, String newAddress) throws ValidationException {
		ValidationUtils.correctIDPassword(id, currentPassword, customers.get(id));
		List<Customer> customers = FileManagement.readFromFile(UserRepository.FILE_NAME);

		for (Customer customer : customers) {
			if (customer.getId().equals(id)) {
				customer.setAddress(newAddress);
				FileManagement.writeToFile(UserRepository.FILE_NAME, customers);
				return true;
			}
		}
		return false;
	}

	// 이메일 수정
	public boolean updateEmail(String id, String currentPassword, String newEmail) throws ValidationException {
		ValidationUtils.correctIDPassword(id, currentPassword, customers.get(id));
		List<Customer> customers = FileManagement.readFromFile(UserRepository.FILE_NAME);

		for (Customer customer : customers) {
			if (customer.getId().equals(id)) {
				customer.setEmail(newEmail);
				FileManagement.writeToFile(UserRepository.FILE_NAME, customers);
				return true;
			}
		}
		return false;
	}

	// 비밀번호 변경
	public boolean changePassword(String id, String currentPassword, String newPassword) throws ValidationException {
		ValidationUtils.correctIDPassword(id, currentPassword, customers.get(id));

		List<Customer> customers = FileManagement.readFromFile(UserRepository.FILE_NAME);

		for (Customer customer : customers) {
			if (customer.getId().equals(id)) {
				customer.setPassword(newPassword);
				FileManagement.writeToFile(UserRepository.FILE_NAME, customers);
				return true;
			}
		}
		return false;
	}

	// 리뷰 작성 기능
	public void writeReview(String customerId, String itemId, int rating, String reviewText) {

		ProductRepository productRepository = new ProductRepository();
		productRepository.addReview(itemId, customerId, rating, reviewText);

		System.out.println("리뷰가 성공적으로 작성되었습니다");
	}

	public String getName() {
		return mallName;

	}

	public Scanner getScanner() {
		return scanner;
	}

	public HashMap<String, Item> getItems() {
		return items;
	}

	public HashMap<String, Customer> getCustomers() {
		return customers;
	}

	public HashMap<String, Manager> getManagers() {
		return managers;
	}

	public HashMap<String, Order> getOrders() {
		return orders;
	}

	public HashMap<String, ArrayList<CartItem>> getCarts() {
		return carts;
	}

	public HashMap<ArrayList<String>, String> getReview() {
		return review;
	}

	public LocalDateTime getShippingDate() {
		return shippingDate;
	}


}
