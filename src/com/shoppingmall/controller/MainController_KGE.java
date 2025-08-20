package com.shoppingmall.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.shoppingmall.models.CartItem;
import com.shoppingmall.models.Customer;
import com.shoppingmall.models.Item;
import com.shoppingmall.models.Order;
import com.shoppingmall.service.ManagerService;
import com.shoppingmall.service.UserService;

public class MainController_KGE {
    private Scanner scanner;
    private ManagerService managerService;
    private UserService userService;

    public MainController_KGE() {
        this.scanner = new Scanner(System.in);
        managerService = new ManagerService("Java Shopping Mall");
        userService = new UserService("Java Shopping Mall");
    }

    // 1. 관리자 모드 주문관리
    public void adminOrderManageMenu() {
        while (true) {
            System.out.println("┌────────────────────────────────────┐");
            System.out.println("│   📦[관리자 모드] 주문 관리             │");
            System.out.println("├────────────────────────────────────┤");
            System.out.println("│  1. 주문 내역 확인                    │");
            System.out.println("│  2. 주문 confirm                    │");
            System.out.println("│  3. 주문 취소                        │");
            System.out.println("│  0. 돌아가기                         │");
            System.out.println("└────────────────────────────────────┘");
            System.out.print("메뉴를 선택하세요: _");
            String menu = scanner.nextLine();
            switch(menu) {
                case "1":
                    managerService.showAllOrders();
                    break;
                case "2":
                    System.out.print("확정할 주문번호를 입력하세요: ");
                    String confirmOrderId = scanner.nextLine().trim();
                    try {
                        Order order = (Order)managerService.getOrders().get(confirmOrderId);
                        managerService.confirmOrder(order != null ? order.getStatus() : null, confirmOrderId);
                        System.out.println("주문이 확정되었습니다.");
                    } catch(Exception e) {
                        System.err.println("확정 실패: " + e.getMessage());
                    }
                    break;
                case "3":
                    System.out.print("취소할 주문번호를 입력하세요: ");
                    String cancelOrderId = scanner.nextLine().trim();
                    try {
                        Order order = (Order)managerService.getOrders().get(cancelOrderId);
                        managerService.cancelOrder(order != null ? order.getStatus() : null, cancelOrderId);
                        System.out.println("주문이 취소되었습니다.");
                    } catch(Exception e) {
                        System.err.println("취소 실패: " + e.getMessage());
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
            System.out.println();
        }
    }

    // 2. 사용자 장바구니 관리
    public void cartManageMenu(String userId) {
        while(true) {
            System.out.println("┌────────────────────────────────────┐");
            System.out.println("│         🛒 장바구니 관리               │");
            System.out.println("├────────────────────────────────────┤");
            System.out.println("│  1. 장바구니 조회                      │");
            System.out.println("│  2. 상품 추가                        │");
            System.out.println("│  3. 수량 변경                        │");
            System.out.println("│  4. 상품 삭제                        │");
            System.out.println("│  5. 장바구니 비우기                    │");
            System.out.println("│  0. 돌아가기                         │");
            System.out.println("└────────────────────────────────────┘");
            System.out.print("메뉴를 선택하세요: _");
            String menu = scanner.nextLine();
            ArrayList<CartItem> cartItems = userService.getCarts().get(userId);
            switch(menu) {
                case "1":
                    if(cartItems == null || cartItems.isEmpty()) {
                        System.out.println("장바구니가 비어 있습니다.");
                    } else {
                        System.out.println("[장바구니 현재 목록]");
                        for(CartItem ci : cartItems) {
                            System.out.println(ci);
                        }
                    }
                    break;
                case "2":
                    System.out.print("추가할 상품의 이름을 입력해주세요: _");
                    String pName = scanner.nextLine();
                    Item item = userService.getItembyName(pName);
                    if(item == null) {
                        System.out.println("존재하지 않는 상품입니다.");
                        break;
                    }
                    System.out.print("추가 수량을 입력하세요: ");
                    try {
                        int qty = Integer.parseInt(scanner.nextLine());
                        userService.getCarts().putIfAbsent(userId, new ArrayList<CartItem>());
                        userService.getCarts().get(userId).add(new CartItem(item, qty));
                        System.out.println("장바구니에 상품이 추가되었습니다.");
                    } catch(Exception e) {
                        System.out.println("추가 실패: " + e.getMessage());
                    }
                    break;
                case "3":
                    System.out.print("수량을 변경할 상품 이름: ");
                    String targetName = scanner.nextLine();
                    boolean found = false;
                    if(cartItems != null) {
                        for(CartItem ci : cartItems) {
                            if(ci.getItem().getName().equals(targetName)) {
                                System.out.print("새 수량을 입력하세요: ");
                                try {
                                    int newQty = Integer.parseInt(scanner.nextLine());
                                    if(newQty <= 0) {
                                        System.out.println("수량은 1 이상이어야 합니다.");
                                        break;
                                    }
                                    cartItems.remove(ci);
                                    cartItems.add(new CartItem(ci.getItem(), newQty));
                                    System.out.println("수량이 변경되었습니다.");
                                    found = true;
                                    break;
                                } catch(Exception e) {
                                    System.out.println("변경 실패: " + e.getMessage());
                                }
                            }
                        }
                    }
                    if(!found) System.out.println("장바구니에 해당 상품이 없습니다.");
                    break;
                case "4":
                    System.out.print("삭제할 상품의 상품명을 입력해주세요: _");
                    String delName = scanner.nextLine();
                    boolean removed = false;
                    if(cartItems != null)
                        removed = cartItems.removeIf(ci -> ci.getItem().getName().equals(delName));
                    if(removed) {
                        System.out.println("장바구니에서 상품이 삭제되었습니다.");
                    } else {
                        System.out.println("장바구니에 해당 상품이 없습니다.");
                    }
                    break;
                case "5":
                    if(cartItems != null) cartItems.clear();
                    System.out.println("장바구니가 비워졌습니다.");
                    break;
                case "0":
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
            System.out.println();
        }
    }

    // 3. 사용자 주문하기
    public void placeOrderMenu(Customer customer) {
        try {
            userService.placeOrder(customer, customer.getAddress());
            System.out.println("주문이 정상적으로 완료되었습니다.");
        } catch(Exception e) {
            System.err.println("주문 실패: " + e.getMessage());
        }
        System.out.println();
    }

    // 4. 사용자 주문내역 조회
    public void showUserOrdersMenu(Customer customer) {
        HashMap<String, Order> orders = userService.getOrders();
        System.out.println("=== 주문 내역 ===");
        boolean hasOrder = false;
        for(Order order : orders.values()) {
            if(order.getCustomer().getId().equals(customer.getId())) {
                System.out.println(order.getOrderDetail());
                hasOrder = true;
            }
        }
        if(!hasOrder) System.out.println("주문내역이 없습니다.");
        System.out.println();
    }
}
