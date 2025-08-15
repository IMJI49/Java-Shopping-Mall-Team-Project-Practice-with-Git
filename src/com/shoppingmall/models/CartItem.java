package com.shoppingmall.models;

import com.shoppingmall.exception.InsufficientStockException;
import com.shoppingmall.exception.ValidationException;

public class CartItem {

    private final Item item; // setItem 제거, 변경 불가능
    private int quantity;    // setQuantity 제거

    public CartItem(Item item, int quantity) throws ValidationException {
        if (item == null) {
            throw new ValidationException("상품이 존재하지 않습니다.");
        }
        if (quantity < 1 || quantity > item.getQuantity()) {
            throw new ValidationException("잘못된 수량입니다.");
        }
        this.item = item;
        this.quantity = quantity;
    }

    // 수량 감소 (1 미만이면 예외)
    public void reduceQuantity(int amount) throws InsufficientStockException {
        if (amount <= 0) return;
        if (quantity - amount < 1) {
            throw new InsufficientStockException("구매 수량은 최소 1개 이상이어야 합니다.");
        }
        this.quantity -= amount;
    }

    // 수량 증가 (재고 초과 시 예외)
    public void addQuantity(int amount) throws InsufficientStockException {
        if (amount <= 0) return;
        if (quantity + amount > item.getQuantity()) {
            throw new InsufficientStockException("재고 수량을 초과할 수 없습니다.");
        }
        this.quantity += amount;
    }

    // 오버플로 방지
    public long getTotalPrice() {
        return Math.multiplyExact((long) item.getPrice(), (long) quantity);
    }

    // Getter
    public Item getItem() { return item; }
    public int getQuantity() { return quantity; }
}
