package com.shoppingmall.util;

import com.shoppingmall.exception.CustomerNotFoundException;
import com.shoppingmall.exception.InsufficientStockException;
import com.shoppingmall.exception.ProductNotFoundException;
import com.shoppingmall.exception.ValidationException;
import com.shoppingmall.models.Customer;
import com.shoppingmall.models.Item;
import com.shoppingmall.models.Order.Status;

public class ValidationUtils {
	/*
	 * exception 여기 throw 빈 문자"", null값 확인 길이 min, max 확인
	 * 
	 */
	private ValidationUtils() {
		// 다른 곳에서 생성자 못만들게끔
	}
	public static void requireNotNullAndEmpty(String str, String message) throws ValidationException {
		if (str == null || str.trim().isEmpty()) {
			throw new ValidationException(message);
		}
	}

	public static void requireMinLength(String str, int minLeng, String message) throws ValidationException {
		requireNotNullAndEmpty(str, message);
		if (str.length() < minLeng) {
			throw new ValidationException(message);
		}
	}

	public static void requireMaxLength(String str, int maxLeng, String message) throws ValidationException {
		requireNotNullAndEmpty(str, message);
		if (str.length() > maxLeng) {
			throw new ValidationException(message);
		}
	}

	public static void requireMinQuantity(int quantity, String message) throws ValidationException {
		if (quantity < 1) {
			throw new ValidationException(message);
		}
	}


	public static void requireNotNullItem(Item item, String message) throws ProductNotFoundException {
		if (item == null) {
			throw new ProductNotFoundException(message);
		} 
	}
	public static void requireNotNullCustomer(Customer customer, String message) throws CustomerNotFoundException {
		if (customer == null) {
			throw new CustomerNotFoundException(message);
		} 
	}
	 
	public static void requireSufficientStock(Item item, int quantity, String message)
			throws InsufficientStockException {
		if ((item.getQuantity() - quantity) < 0) {
			throw new InsufficientStockException(message);
		}
	}
	public static void correctIDPassword(String id, String password, Customer customer) throws ValidationException {
		if (customer.getId() != id) {
			throw new ValidationException("아이디 혹은 비밀번호가 일치하지 안습니다.");
		}
		if (customer.getPassword() != password) {
			throw new ValidationException("아이디 혹은 비밀번호가 일치하지 안습니다.");
		}
	}
	public static void orderPendingCheck(Status status, String string) {
		
	}
	
	
}
