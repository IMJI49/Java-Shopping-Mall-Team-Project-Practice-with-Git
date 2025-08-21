package com.shoppingmall.service;

import java.time.LocalDateTime;

import com.shoppingmall.exception.ValidationException;
import com.shoppingmall.models.Order;
import com.shoppingmall.models.Order.Status;

public class UserService_modifyKGE {
	public void completeDelivery(String orderID) throws ValidationException {
	    Order order = orders.get(orderID);
	    if (order == null) {
	        throw new ValidationException("해당 주문을 찾을 수 없습니다: " + orderID);
	    }

	    if (order.getStatus() != Status.SHIPPING) {
	        throw new ValidationException("배송 중 상태에서만 배송 완료가 가능합니다.");
	    }

	    order.setStatus(Status.DELIVERED);
	    shippingDate = LocalDateTime.now();
	    System.out.printf("✅ [%s] 주문의 배송이 완료되었습니다.\n", orderID);
	}

	public void cancelOrder(String orderID) throws ValidationException {
	    Order order = orders.get(orderID);
	    if (order == null) {
	        throw new ValidationException("해당 주문을 찾을 수 없습니다: " + orderID);
	    }

	    if (order.getStatus() != Status.PENDING) {
	        throw new ValidationException("PENDING 상태에서만 취소 가능합니다.");
	    }

	    order.setStatus(Status.CANCELLED);
	    System.err.printf("⚠ 주문 [%s]가 취소되었습니다.\n", orderID);
	}
}
