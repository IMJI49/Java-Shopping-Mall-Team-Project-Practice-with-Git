package com.shoppingmall.test;

import java.util.List;

import com.shoppingmall.models.Item;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.repository.ProductRepository;

public class ProductTest {
	public static void main(String[] args) {
        System.out.println("=== 제품 repository 테스트 시작 ===\n");
        
        ProductRepository repository = new ProductRepository();
        
        // 테스트
        testSaveNewItem(repository);
        testFindByPriceRange(repository);
        testFindByCategory(repository);
        testFindByDesc(repository);
        testFindByNameExists(repository);
        testFindByNameNotExists(repository);
        // 리뷰, 평점 테스트
        testAddReview(repository);
        testReviews(repository);
        testAverageRating(repository);
        testReviewForNotExists(repository);
        
        
        System.out.println("\n=== ✅ 모든 테스트를 완료했습니다. ===");
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

	// 새 상품 저장 테스트
    private static void testSaveNewItem(ProductRepository repository) {
        System.out.println("\n 테스트 : 새 상품 저장");
        System.out.println("새로운 상품을 저장합니다.");
        System.out.println("=".repeat(20));
        
        // 새 상품 생성
        Item newItem = new Item("스마트폰", "전자제품", 899000, 30, "스마트폰입니다.");
        
        // 상품 저장
        Item savedItem = repository.save(newItem);
        
        // 결과 출력
        if (savedItem != null) {
            System.out.println("== ✅ 상품 저장 성공! ==");
            System.out.println("저장된 상품: " + savedItem.getName());
        } else {
            System.out.println("== ❌ 상품 저장 실패 ==");
        }
    }
    
    // 카테고리로 검색
    private static void testFindByCategory(ProductRepository repository) {
        System.out.println("\n 테스트 : 카테고리로 검색");
        System.out.println("'전자제품' 카테고리로 검색.");
        System.out.println("=".repeat(20));
        
        repository.findByCategory("전자제품");

        System.out.println("== ✅ 검색 메서드 실행 완료! ==");

    }

    // 설명으로 검색
    private static void testFindByDesc(ProductRepository repository) {
        System.out.println("\n 테스트 : 설명으로 검색");
        System.out.println("'입니다' 설명으로 검색.");
        System.out.println("=".repeat(20));
        
        repository.findByDesc("입니다");

        System.out.println("== ✅ 검색 메서드 실행 완료! ==");

    }
    
    
    
    // 이름으로 검색 (존재하는 상품)
    private static void testFindByNameExists(ProductRepository repository) {
        System.out.println("\n 테스트 : 이름으로 검색 (존재하는 상품)");
        System.out.println("'노트북'으로 검색합니다.");
        System.out.println("=".repeat(20));
         
        repository.findByName("노트북");
        
        System.out.println("== ✅ 검색 메서드 실행 완료! ==");

    }
    
    // 이름으로 검색 (존재하지 않는 상품)
    private static void testFindByNameNotExists(ProductRepository repository) {
        System.out.println("\n 테스트 : 이름으로 검색 (존재하지 않는 상품)");
        System.out.println("'존재하지않는상품'으로 검색합니다.");
        System.out.println("=".repeat(20));
        
        repository.findByName("존재하지 않는 상품");
        
        System.out.println("== ✅ 검색 메서드 실행 완료! ==");

    }
    
    // 가격대로 검색
    private static void testFindByPriceRange(ProductRepository repository) {
    	 System.out.println("\n 테스트 : 가격대로 검색");
         System.out.println("'100원~ 10000원'으로 검색합니다.");
         System.out.println("=".repeat(20));
         
         repository.findByPriceRange(100, 10000);
         
         System.out.println("== ✅ 검색 메서드 실행 완료! ==");

		
	}
    
    
    
    
    
    
    // 리뷰, 평점 메서드들
    

    // 리뷰 추가 테스트
    private static void testAddReview(ProductRepository repository) {
        System.out.println("\n 테스트 : 리뷰 추가");
        System.out.println("저장된 상품에 리뷰를 추가하고 확인합니다.");
        System.out.println("=".repeat(20));

        // 새 상품 생성 및 저장
        Item testItem = new Item("테스트상품", "테스트", 10000, 10, "리뷰 테스트용 상품");
        Item savedItem = repository.save(testItem);
        
        if (savedItem != null) {
            String itemId = savedItem.getItemID();
            System.out.println("상품 저장 완료: " + itemId);
            
            // 리뷰 추가 전 평점 확인
            double beforeRating = repository.getAverageRating(itemId);
            System.out.println("리뷰 추가 전 평균 평점: " + beforeRating);
            
            // 리뷰 추가
            repository.addReview(itemId, "USER_001", 5, "정말 좋은 상품입니다!");
            
            // 리뷰 추가 후 평점 확인
            double afterRating = repository.getAverageRating(itemId);
            System.out.println("리뷰 추가 후 평균 평점: " + afterRating);
            
            // 리뷰 목록 확인
            Item item = repository.getItemById(itemId);
            System.out.println("\n리뷰 목록:");
            for (int i = 0; i < item.getReview().size(); i++) {
                System.out.println(
                    (i + 1) +
                    ". 평점: " + item.getRatings().get(i) + "/5 | 리뷰: " + item.getReview().get(i) +
                    " | 작성자: " + item.getReviewerIds().get(i).substring(0, 3) + "****"
                ); 	        // 개인정보 노출 위험 최소화를 위해 아이디 앞 3자리만 공개 + ****로 항상 표시
            }

            
            // 결과 검증
            if (afterRating == 5.0 && item.getReview().size() != 0) {
                System.out.println("== ✅ 리뷰 추가 성공! 평점이 정상적으로 계산됨 ==");
            } else {
                System.out.println("== ❌ 리뷰 추가 실패 또는 평점 계산 오류 ==");
            }
        } else {
            System.out.println("== ❌ 테스트용 상품 저장 실패 ==");
        }
    }

    // 여러 리뷰 추가 테스트
    private static void testReviews(ProductRepository repository) {
        System.out.println("\n 테스트 : 여러 리뷰 추가 테스트");
        System.out.println("한 상품에 여러 리뷰를 추가합니다.");
        System.out.println("=".repeat(20));

        Item testItem = new Item("테스트상품", "테스트", 10000, 10, "리뷰 테스트용 상품");
		testItem.setItemID("ITEM_001");
		repository.save(testItem);
		
        repository.addReview("ITEM_001", "USER_002", 4, "또 살게요");
        repository.addReview("ITEM_001", "USER_003", 3, "보통이에요");
        repository.addReview("ITEM_001", "USER_004", 5, "완전 추천!");
        
        // 리뷰 목록 확인
        Item item = repository.getItemById("ITEM_001");
        System.out.println("\n리뷰 목록:");
        for (int i = 0; i < item.getReview().size(); i++) {
            System.out.println(
                (i + 1) +
                ". 평점: " + item.getRatings().get(i) + "/5 | 리뷰: " + item.getReview().get(i) +
                " | 작성자: " + item.getReviewerIds().get(i).substring(0, 3) + "****"
            ); 	        // 개인정보 노출 위험 최소화를 위해 아이디 앞 3자리만 공개 + ****로 항상 표시
        }

        System.out.println("== ✅ 여러 리뷰 추가 테스트 완료! ==");
    }

    // 평균 평점 계산 테스트
    private static void testAverageRating(ProductRepository repository) {
        System.out.println("\n 테스트 : 평균 평점 계산");
        System.out.println("상품의 평균 평점을 계산합니다.");
        System.out.println("=".repeat(20));

	
        double avgRating = repository.getAverageRating("ITEM_001");
        System.out.println("ITEM_001의 평균 평점: " + String.format("%.1f", avgRating));


        
        // 평점이 없는 상품 테스트
        
        Item testItem = new Item("테스트상품2", "테스트2", 10000, 10, "리뷰 테스트용 상품");
		testItem.setItemID("ITEM_002");
        
        double avgRating2 = repository.getAverageRating("ITEM_002");
        System.out.println("ITEM_002의 평균 평점: " + String.format("%.1f", avgRating2));

        System.out.println("== ✅ 평균 평점 계산 테스트 완료! ==");
    }

    // 존재하지 않는 상품에 리뷰 추가 테스트
    private static void testReviewForNotExists(ProductRepository repository) {
        System.out.println("\n 테스트 : 존재하지 않는 상품에 리뷰 추가");
        System.out.println("존재하지 않는 상품 ID로 리뷰 추가를 시도합니다.");
        System.out.println("=".repeat(20));


        try {

        	repository.addReview("NONEXISTENT_ITEM", "USER_005", 5, "이 리뷰는 추가되지 않을 것입니다");

        	
        	// 리뷰 목록 확인
            Item item = repository.getItemById("NONEXISTENT_ITEM");
            System.out.println("\n리뷰 목록:");
            for (int i = 0; i < item.getReview().size(); i++) {
                System.out.println(
                    (i + 1) +
                    ". 평점: " + item.getRatings().get(i) + "/5 | 리뷰: " + item.getReview().get(i) +
                    " | 작성자: " + item.getReviewerIds().get(i).substring(0, 3) + "****"
                ); 	        // 개인정보 노출 위험 최소화를 위해 아이디 앞 3자리만 공개 + ****로 항상 표시
            }
		} catch (Exception e) {
		  System.err.println("== ✅ 리뷰 테스트 성공 : 존재하지 않는 상품의 리뷰가 추가되지 않았습니다.");
		}
        

        
        System.out.println("== ✅ 존재하지 않는 상품 리뷰 테스트 완료! ==");
    }
    
    
}
