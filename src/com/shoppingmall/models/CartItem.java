package com.shoppingmall.models;


import com.shoppingmall.exception.InsufficientStockException;
import com.shoppingmall.exception.ShoppingMallException;
import com.shoppingmall.exception.ValidationException;
import com.shoppingmall.util.ValidationUtils;

public class CartItem {

    private final Item item; // setItem 제거, 변경 불가능
    private int quantity;    // setQuantity 제거
    private long totalPrice;
    public CartItem(Item item, int quantity) throws ShoppingMallException {
    	ValidationUtils.requireNotNullItem(item, "상품이 존재하지 않습니다.");
    	ValidationUtils.requireMinQuantity(quantity, "잘못된 수량입니다.");
    	ValidationUtils.requireSufficientStock(item, quantity);
        this.item = item;
        this.quantity = quantity;
        totalPrice = getTotalPrice();
    }

    // 수량 감소 (1 미만이면 예외)
    public void reduceQuantity(int amount) throws ValidationException {
        if (amount <= 0) return;

        ValidationUtils.requireMinQuantity(amount, "구매 수량은 최소 1개 이상이어야 합니다.");
        this.quantity -= amount;
    }

    // 수량 증가 (재고 초과 시 예외)
    public void addQuantity(int amount) throws InsufficientStockException {
        if (amount <= 0) return;
        ValidationUtils.requireSufficientStock(item, quantity);
        this.quantity += amount;
    }

    // 오버플로 방지
    public long getTotalPrice() {
        return (long) item.getPrice() * (long) quantity;
    }
    public long discountPrice(double rate) {
    	return totalPrice *= (1-rate);
    }
    // Getter
    public Item getItem() { return item; }
    public int getQuantity() { return quantity; }

	@Override
	public String toString() {
		return String.format("- %s | 가격: %,d | 수량: %d | 합계: %,d원\n",
				getItem().getName(), getItem().getPrice(),getQuantity(), getTotalPrice());
			
	}
    
}
