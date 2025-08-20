package com.shoppingmall.controller;

import java.util.Scanner;

import com.shoppingmall.exception.ShoppingMallException;
import com.shoppingmall.models.Item;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.repository.ProductRepository;
import com.shoppingmall.service.UserService;
import com.shoppingmall.util.Constants;
import com.shoppingmall.util.ValidationUtils;

public class Maincontroller_IJH {
	private UserService userService;
	private ProductRepository productRepository;
	
	public Maincontroller_IJH() {
		userService = new UserService("Java Shopping Mall");
		productRepository = new ProductRepository();
	}
	public void itemController(Scanner scanner) throws ShoppingMallException {
		while(true) {
			System.out.println("┌────────────────────────────────────┐");
			System.out.println("│      📦 [관리자] 상품 관리              │");
			System.out.println("├────────────────────────────────────┤");
			System.out.println("│  1. 상품 등록                         │");
			System.out.println("│  2. 상품 수정                         │");
			System.out.println("│  3. 상품 삭제                         │");
			System.out.println("│  4. 재고 관리                         │");
			System.out.println("│  5. 상품 조회                         │");
			System.out.println("│  0. 돌아가기                          │");
			System.out.println("└────────────────────────────────────┘");
			System.out.print("메뉴를 선택하세요: _");
			
			String menu = scanner.nextLine();
			switch (menu) {
			case "0":
				return;
			case "1":
				System.out.println("\n===========   상품 등록   ============");
				System.out.print("상품 명을 입력해 주세요: _");
				String name = scanner.nextLine();
				System.out.print("상품 카테고리를 입력해 주세요: _");
				String category = scanner.nextLine();
				System.out.print("상품 가격을 입력해 주세요: _");
				String sPrice = scanner.nextLine().trim().replaceAll("원., ", "");
				int price = Integer.parseInt(sPrice);
				System.out.print("상품 수량을 입력해 주세요: _");
				String sQuantity = scanner.nextLine().trim().replaceAll("개., ", "");
				int quantity = Integer.parseInt(sQuantity);
				System.out.print("상품 설명을 입력해 주세요: _");
				String description = scanner.nextLine();
				Item newItem = new Item(name, category, price, quantity, description);
				productRepository.save(newItem);
				System.out.println("상품이 등록되었습니다!");
				System.out.println("=====================================");
				break;
			case "2":
				/*
				 *  가격, 설명
				 *  제품 명 받고 제품 명을 통한 item받아서 set으로 수정
				 */
				System.out.println("\n==========  상품 수정  ===========");
				System.out.print("변경 할 상품의 이름을 입력 해 주세요. \n변경하지 않을 경우 n을 입력 해 주세요 :_");
				String modifyItemName = scanner.nextLine().trim();
				ValidationUtils.requireNotNullAndEmpty(modifyItemName, "빈 문자를 입력하지 말아 주세요");
				if (modifyItemName.toLowerCase() == "n") {
					break;
				}
				Item modifyItem = userService.getItembyName(modifyItemName);
				
				ValidationUtils.requireNotNullItem(modifyItem, "변경 할 제품이 없습니다.");
				System.out.print("상품 가격, 상품 개수, 상품 설명을 변경 할 수 있습니다.\n 어떤 것을 변경하시겠습니까?\n가격, 개수, 설명을 입력 해 주세요(예. 개수, 가격)\n 변경 사항이 없을 시 n을 입력해주세요 :_");
				String modifyingItemList = scanner.nextLine();
				ValidationUtils.requireNotNullAndEmpty(modifyingItemList, "빈 문자를 입력하지 말아 주세요");
				if (modifyingItemList.toLowerCase() == "n") {
					break;
				}
				if (modifyingItemList.contains("가격")) {
					System.out.print("상품의 가격을 입력해주세요:_");
					sPrice = scanner.nextLine().trim().replaceAll("원., ", "");
					price = Integer.parseInt(sPrice);
					modifyItem.setPrice(price);
				}
				if (modifyingItemList.contains("개수")) {
					System.out.print("상품의 개수를 입력해주세요:_");
					sQuantity = scanner.nextLine().trim().replaceAll("개., ", "");
					ValidationUtils.requireMaxLength(sQuantity, 7, "상품 개수는 999,999개를 넘을 수 없습니다.");
					quantity = Integer.parseInt(sQuantity);
					modifyItem.setQuantity(quantity);
				}
				if (modifyingItemList.contains("설명")) {
					System.out.print("상품 설명을 입력해 주세요:_");
					description = scanner.nextLine();
					ValidationUtils.requireMaxLength(description, 500, "상품 설명은 500자를 넘을 수 없습니다.");
					modifyItem.setDescription(description);
				}
				FileManagement.writeToFile(Constants.PRODUCT_DATA_FILE, userService.getItems().values().stream().toList());
				System.out.println("상품이 수정되었습니다!");
				System.out.println("================================\n");
				break;
			case "3":
				System.out.println("\n=========   상품 삭제   ==========");
				System.out.print("삭제할 상품의 이름을 검색하세요, 삭제 하지 않을 경우 n을 입력 해 주세요: _");
				String deleteItemName = scanner.nextLine().trim();
				ValidationUtils.requireNotNullAndEmpty(deleteItemName, "빈 문자를 입력하지 말아 주세요");
				if (deleteItemName.toLowerCase() == "n") {
					break;
				}
				Item deleteItem = userService.getItembyName(deleteItemName);
				ValidationUtils.requireNotNullItem(deleteItem, "삭제 할 제품이 없습니다.");
				FileManagement.writeToFile(Constants.PRODUCT_DATA_FILE, userService.deleteItem(deleteItem).values().stream().toList());
				System.out.println("성공적으로 삭제되었습니다.");
				System.out.println("==================================\n");
				break;
			case "4":
				//재고 관리
				System.out.println("\n=========   재고 관리   ============");
				System.out.print("상품 재고 변경 할 상품의 이름을 입력 해 주세요. \n변경하지 않을 경우 n을 입력 해 주세요");
				modifyItemName = scanner.nextLine().trim();
				ValidationUtils.requireNotNullAndEmpty(modifyItemName, "빈 문자를 입력하지 말아 주세요");
				if (modifyItemName.toLowerCase() == "n") {
					break;
				}
				modifyItem = userService.getItembyName(modifyItemName);
				System.out.print("상품 개수를 증가시키겠습니까? 감소시키겠습니까?(추가, 감소), n을 입력하면 취소 됩니다 :_");
				String pmselect = scanner.nextLine().trim();
				ValidationUtils.requireNotNullAndEmpty(pmselect, "빈 문자를 입력하지 말아 주세요");
				if (pmselect.toLowerCase() == "n") {
					break;
				}
				if (pmselect.equals("추가") || pmselect.equals("증가") ) {
					System.out.print("개수를 입력해주세요: _");
					sQuantity = scanner.nextLine().trim().replaceAll("개., ", "");
					ValidationUtils.requireMaxLength(sQuantity, 7, "상품 개수는 999,999개를 넘을 수 없습니다.");
					quantity = Integer.parseInt(sQuantity);
					modifyItem.productPlusStock(quantity);
				}
				if (pmselect.equals("감소")) {
					System.out.print("개수를 입력해주세요: _");
					sQuantity = scanner.nextLine().trim().replaceAll("개., ", "");
					ValidationUtils.requireMaxLength(sQuantity, 7, "상품 개수는 999,999개를 넘을 수 없습니다.");
					quantity = Integer.parseInt(sQuantity);
					modifyItem.productMinusStock(quantity);
				}
				FileManagement.writeToFile(Constants.PRODUCT_DATA_FILE, userService.getItems().values().stream().toList());
				System.out.println("===================================\n");
				break;
			case "5":
				// 상품 둘러보기 메소드로 바로 가기
				break;
			default:
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				break;
			}
		}
	}
}


	