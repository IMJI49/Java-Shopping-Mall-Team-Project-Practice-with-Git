package com.shoppingmall.util;

import javax.naming.InsufficientResourcesException;

import com.shoppingmall.exception.CustomerNotFoundException;
import com.shoppingmall.exception.ProductNotFoundException;
import com.shoppingmall.exception.ShoppingMallException;
import com.shoppingmall.exception.ValidationException;
import com.shoppingmall.models.Customer;
import com.shoppingmall.models.Item;

public class ValidationUtils {
	/*
	 * exception 여기 throw 빈 문자"", null값 확인 길이 min, max 확인
	 * 
	 */
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

	@SuppressWarnings("unused")
	public static void requireNotNullObject(Object obj, String message) throws ShoppingMallException {
		if (obj.getClass() == Item.class && obj == null) {
			throw new ProductNotFoundException(message);
		} else if (obj.getClass() == Customer.class && obj == null) {
			throw new CustomerNotFoundException(message);
		}
	}

	public static void requireSufficientStock(Item item, int quantity, String message)
			throws InsufficientResourcesException {
		if ((item.getQuantity() - quantity) < 0) {
			throw new InsufficientResourcesException(message);
		}
	}

	private ValidationUtils() {
	}
}
