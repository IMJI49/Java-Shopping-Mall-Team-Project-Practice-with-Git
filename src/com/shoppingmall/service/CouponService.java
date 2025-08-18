package com.shoppingmall.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.shoppingmall.models.CartItem;
import com.shoppingmall.models.Customer;
import com.shoppingmall.models.Order;

public class CouponService {
	private HashMap<String, Order> orders;
	// placeOrder하기전에 써야함
	public void couponUse(Customer customer, String type, HashMap<String, ArrayList<CartItem>> cart) {
		customer.couponUse(type);
		ArrayList<CartItem> cartItems = cart.get(customer.getId());
		switch (type) {
		case "A":
			for (CartItem cartItem : cartItems) {
				cartItem.discountPrice(0.1);
			}
			break;
		case "B":
			for (CartItem cartItem : cartItems) {
				cartItem.discountPrice(0.05);
			}
			break;
		case "C":
			for (CartItem cartItem : cartItems) {
				cartItem.discountPrice(0.01);
			}
			break;
		default:
			System.out.println("쿠폰이 없습니다.");
			break;
		}
	}
	
	// placeOrder에 들어가야함
	public void addPoint(Customer customer) {
		Order order = orders.get(customer.getId());
		customer.addPoint(order);
	}
}

	