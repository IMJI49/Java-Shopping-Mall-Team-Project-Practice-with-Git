package com.shoppingmall.repository;

import java.util.ArrayList;
import java.util.List;

import com.shoppingmall.models.Item;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.util.Constants;

public class ProductRepository {
	
	// 파일명 상수
	private static final String FILE_NAME = Constants.PRODUCT_DATA_FILE;

	
	
	public ProductRepository() {
		
		initialize();
		
	}
	
	// 상품 생성 (초기화)
	private void initialize() {
		List<Item> items = FileManagement.readFromFile(FILE_NAME);
		
		// 기본 상품이 없으면 생성
		if(items.isEmpty()) {
			
			Item item1 = new Item("노트북","전자제품",1790000,50,"노트북입니다");
			Item item2 = new Item("티셔츠","의류",19800,1000,"티셔츠입니다");
			Item item3 = new Item("커피","식품",8000,500,"커피입니다");
			Item item4 = new Item("소설","도서",12000,80,"소설입니다");
			Item item5 = new Item("문구류","기타",3000,700,"문구류입니다");
		
			items.add(item1);
			items.add(item2);
			items.add(item3);
			items.add(item4);
			items.add(item5);
			
			FileManagement.writeToFile(FILE_NAME, items);
			System.out.println("기본 상품이 생성되었습니다.");
		
		};
	}
	
	
	// 상품 저장
	public Item save(Item item) {
		// 기존 상품 목록 조회
		List<Item> items = FileManagement.readFromFile(FILE_NAME);
		
		// 새 상품 추가
		items.add(item);
		
		// 파일에 저장
		FileManagement.writeToFile(FILE_NAME, items);
		
		return item;
	}
	
		
	// 카테고리로 상품 검색
	public Object findByCategory(String category) {
	List<Item> items = FileManagement.readFromFile(FILE_NAME);
	
	List<Item> foundItems = items.stream()
			.filter(u -> u.getCategory().equals(category))
			.toList();

	if(foundItems.isEmpty()) {
		return null;
	}
	
	foundItems.forEach(System.out::println);
	
	return foundItems;
	}
		
		
	// 상품명으로 상품 검색
	public Object findByName(String name) {
	List<Item> items = FileManagement.readFromFile(FILE_NAME);
	
	List<Item> foundItems = items.stream()
							.filter(u -> u.getName().equals(name))
							.toList();
	
	if(foundItems.isEmpty()) {
		return null;
	}
	
	foundItems.forEach(System.out::println);
	
	return foundItems;
			
	}

		
		
	// (1. 1만원 미만, 2. 1-5만원, 3. 5-10만원,4. 10만원 이상)
	// 가격대로 상품 검색
	public Object findByPriceRange(int minPrice, int maxPrice) {
	List<Item> items = FileManagement.readFromFile(FILE_NAME);
	
	List<Item> foundItems = items.stream()
							.filter(i -> i.getPrice() >= minPrice &&
										i.getPrice() <=maxPrice)
							.toList();
	
	if(foundItems.isEmpty()) {
		return null;
	}
	
	foundItems.forEach(System.out::println);
	
	return foundItems;
			
	}
		
		
	// itemId로 상품 검색 (showItemSummary 메서드, getAverageRating에 필요)
	public Item getItemById(String itemId) {
	    List<Item> items = FileManagement.readFromFile(FILE_NAME);

	    for (Item item : items) {
	        if (item.getItemID().equals(itemId)) {
	            if (item.getReview() == null) item.setReview(new ArrayList<>());
	            if (item.getReviewerIds() == null) item.setReviewerIds(new ArrayList<>());
	            if (item.getRatings() == null) item.setRatings(new ArrayList<>());
	            return item;
	        }
	    }
	    return null; 
	}
		
	
	// Item 리뷰 추가 
	public void addReview(String itemId, String customerId, int rating, String reviewText) {
	    List<Item> items = FileManagement.readFromFile(FILE_NAME);
	    
	    Item item = items.stream()
	                    .filter(i -> i.getItemID().equals(itemId))
	                    .findFirst()
	                    .orElse(null);
	    
	    if (item != null) {
	    	
	        if (item.getReview() == null) {
	            item.setReview(new ArrayList<>());
	        }
	    	
	        item.getReview().add(reviewText);
	        item.getReviewerIds().add(customerId);
	        item.addReviewRating(rating)
	        
	        // Item 리스트 다시 저장
	        FileManagement.writeToFile(FILE_NAME, items);
	        
	    System.out.println("리뷰가 성공적으로 추가되었습니다");
	    } else {
	    	System.out.println("해당 ID의 상품을 찾을 수 없습니다: " + itemId);
	    }
	}

	// 한 상품의 모든 리뷰 출력
	public void showItemReviews(String itemId) {
	    Item item = getItemById(itemId);
	    
	    if (item != null) {
	        System.out.println("=== " + item.getName() + "의 전체 리뷰 요약 ===");
	        System.out.println("평균 평점: " + String.format("%.1f", getRating(itemId)));
	        System.out.println("총 리뷰 개수: " + item.getReview().size());
	        
	        if (!item.getReview().isEmpty()) {
	            System.out.println("\n리뷰 목록:");
	            for (int i = 0; i < item.getReview().size(); i++) {
	                System.out.println(
	                		(i+1) +
	                		". 평점: " + item.getRatings().get(i) +  
	                		"/5 | 리뷰: "
	                		+ item.getReview().get(i) + 
	                        " | 작성자: "
	                		+ item.getReviewerIds().get(i).substring(0,3) + "****"); 
	                		// 개인정보 노출 위험 최소화를 위해 아이디 앞 3자리만 공개 + ****로 항상 표시
	            }
	        } else {
	            System.out.println("아직 리뷰가 없습니다.");
	        }
	        System.out.println("================================");
	    } else {
	        System.out.println("해당 ID의 상품을 찾을 수 없습니다: " + itemId);
	    }
	}

		
}


// 
