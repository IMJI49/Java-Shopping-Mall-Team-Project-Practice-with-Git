package com.shoppingmall.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Order {

    public enum Status { PENDING, CONFIRM, SHIPPING, DELIVERED, CANCELLED }

    private static long orderSeq = 1; // 주문 일련번호
    private String orderID;
    private Customer customer;
    private LocalDateTime orderDate;

    private Status status;
    private List<CartItem> cartItems;
    private long totalAmount;
    private String shippingAddress;
    // 리뷰 관련
    private boolean reviewPromptShown; // 리뷰 창 한 번만 뜨게

    // 생성자
    public Order(Customer customer, List<CartItem> cartItems, String shippingAddress) {
        this.orderID = "O" + String.format("%08d", orderSeq++);
        this.customer = customer;
        this.orderDate = LocalDateTime.now();
        this.status = Status.PENDING;
        this.cartItems = new ArrayList<>(cartItems);
        this.totalAmount = calculateTotal();
        reviewPromptShown = false;
        this.shippingAddress = (shippingAddress == null || shippingAddress.isBlank()) 
            ? customer.getAddress() : shippingAddress;
    }

    public boolean isReviewPromptShown() {
		return reviewPromptShown;
	}

	public void setReviewPromptShown(boolean reviewPromptShown) {
		this.reviewPromptShown = reviewPromptShown;
	}

	public Order(Customer customer, List<CartItem> cartItems) {
        this(customer, cartItems, customer.getAddress());
    }

    // 총금액 계산 - overflow 방지
    private long calculateTotal() {
        return cartItems.stream()
                        .mapToLong(CartItem::getTotalPrice)
                        .sum();
    }

    @Override
    public String toString() {
        return String.format(
            "주문번호: %s\n주문일: %s\n상태: %s\n총액: %d원",
            orderID, orderDate, status, totalAmount
        );
    }

    public String getOrderDetail() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString())
          .append("\n배송주소: ").append(shippingAddress)
          .append("\n주문상품:\n");
        for (CartItem ci : cartItems) {
            sb.append("- ").append(ci.getItem().getName())
              .append(" x ").append(ci.getQuantity())
              .append(" = ").append(ci.getTotalPrice()).append("원\n");
        }
        return sb.toString();
    }

    // Getter

    public void setStatus(Status status) {
    	this.status = status;
    }
    public String getOrderID() { return orderID; }
    public Customer getCustomer() { return customer; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public Status getStatus() { return status; }
    public List<CartItem> getCartItems() { return cartItems; }
    public long getTotalAmount() { return totalAmount; }
}
