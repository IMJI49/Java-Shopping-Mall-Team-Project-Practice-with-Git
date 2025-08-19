package com.shoppingmall.repository;

import java.util.List;
import java.util.stream.Collectors;

import com.shoppingmall.models.Order;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.util.Constants;

public class OrderRepository {
	public static final String FILE_NAME = Constants.ORDER_DATA_FILE;

	// 주문 저장
	public Order save(Order order) {
		// 기존 주문 목록 조회
		List<Order> orders = FileManagement.readFromFile(FILE_NAME);
		
		// 새 주문 추가
		orders.add(order);
		
		// 파일에 저장
		FileManagement.writeToFile(FILE_NAME, orders);
		
		return order;
	}
	
	
	
	// 주문내역 조회 1. 사용자용 : 본인의 주문내역만 조회
	public List<Order> findByUserId(String userId){
		List<Order> Orders = FileManagement.readFromFile(FILE_NAME);
		return Orders.stream()
					 .filter(order -> order.getOrderID().equals(userId))
				     .collect(Collectors.toList());	// 리스트로 수집

	}
	
	// 주문내역 조회 2. 관리자용1 : 모든 주문내역만 조회
	public List<Order> findAll(){
		
		List<Order> Orders = FileManagement.readFromFile(FILE_NAME);
		return Orders;

	}
	
    // 주문내역 조회 3. 관리자용2 : 상태별 주문내역 조회
    public List<Order> findByStatus(Order.Status status) {
        List<Order> allOrders = FileManagement.readFromFile(FILE_NAME);
        return allOrders.stream()
		                .filter(order -> order.getStatus() == status)
		                .collect(Collectors.toList());
    }
	

}
