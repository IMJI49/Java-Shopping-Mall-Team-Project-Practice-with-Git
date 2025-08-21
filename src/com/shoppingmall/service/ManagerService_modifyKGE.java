package com.shoppingmall.service;

import com.shoppingmall.exception.ValidationException;
import com.shoppingmall.models.Order;
import com.shoppingmall.models.Order.Status;
import com.shoppingmall.util.ValidationUtils;

public class ManagerService_modifyKGE {
	public void confirmOrder(String orderID) throws ValidationException {
	    Order order = orders.get(orderID);
	    if (order == null) {
	        throw new ValidationException("해당 주문을 찾을 수 없습니다: " + orderID);
	    }

	    ValidationUtils.orderPendingCheck(order.getStatus(), "현재 상태에서는 주문 확정이 불가능합니다.");
	    order.setStatus(Status.CONFIRM);
	}
	public void startShipping(String orderID) throws ValidationException {
	    Order order = orders.get(orderID);
	    if (order == null) {
	        throw new ValidationException("해당 주문을 찾을 수 없습니다: " + orderID);
	    }

	    if (order.getStatus() != Status.CONFIRM) {
	        throw new ValidationException("확정된 주문만 배송을 시작할 수 있습니다.");
	    }

	    order.setStatus(Status.SHIPPING);
	    System.out.printf("📦 %s님 주문의 배송이 시작되었습니다. (주문번호 : %s)\n",
	        order.getCustomer().getName(), orderID);
	}
}
