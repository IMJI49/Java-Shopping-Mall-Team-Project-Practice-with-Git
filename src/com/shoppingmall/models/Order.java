package com.shoppingmall.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.shoppingmall.exception.ValidationException;
import com.shoppingmall.util.ValidationUtils;

public class Order {

    public enum Status { PENDING, CONFIRM, SHIPPING, DELIVERED, CANCELLED }

    private static long orderSeq = 1; // 주문 일련번호
    private String orderID;
    private Customer customer;
    private LocalDateTime orderDate;
    private LocalDateTime shippingDate; // 배송 완료 날짜
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
        this.shippingAddress = (shippingAddress == null || shippingAddress.isBlank()) 
            ? customer.getAddress() : shippingAddress;
        this.reviewPromptShown = false;
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

    public void confirmOrder() throws ValidationException {
        ValidationUtils.orderPendingCheck(status, "현재 상태에서는 주문 확정이 불가능합니다.");
        status = Status.CONFIRM;
    }

    public void startShipping() throws ValidationException {
        if (status != Status.CONFIRM) throw new ValidationException("확정된 주문만 배송을 시작할 수 있습니다.");
        status = Status.SHIPPING;
        System.out.printf("📦 %s님 주문의 배송이 시작되었습니다. (주문번호 : %s)\n",customer.getId(), orderID);
    }

    public void completeDelivery() throws ValidationException {
        if (status != Status.SHIPPING) throw new ValidationException("배송 중 상태에서만 배송 완료가 가능합니다.");
        status = Status.DELIVERED;
        shippingDate = LocalDateTime.now();
        System.out.printf("✅ [%s] 주문의 배송이 완료되었습니다./n", orderID);
    }

    public void cancelOrder() throws ValidationException {
        if (status != Status.PENDING) throw new ValidationException("PENDING 상태에서만 취소 가능합니다.");
        status = Status.CANCELLED;
        System.err.printf("⚠ 주문 [%s]가 취소되었습니다./n", orderID);
    }

    // 3일 지난 배송 자동 완료
    public void autoCompleteDeliveryIfOver3Days() {
        if (status == Status.SHIPPING && 
            shippingDate != null && 
            shippingDate.plusDays(3).isBefore(LocalDateTime.now())) {
            status = Status.DELIVERED;
            System.out.printf("📦 주문 [%s]은 발송 3일 경과로 자동 완료 처리되었습니다.\n", orderID);
        }
    }

    // 리뷰 안내 (배송 완료 후 1회만)
    public void promptReview() {
        if (status == Status.DELIVERED && !reviewPromptShown) {
            System.out.println("리뷰를 작성해 주세요. (평점 1~5, 내용 3~500자)");
            reviewPromptShown = true;
        }
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
    public String getOrderId() { return orderID; }
    public Customer getCustomer() { return customer; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public Status getStatus() { return status; }
    public List<CartItem> getCartItems() { return cartItems; }
    public long getTotalAmount() { return totalAmount; }
}
