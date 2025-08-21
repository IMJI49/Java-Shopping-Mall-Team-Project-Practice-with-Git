package com.shoppingmall.repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;



import com.shoppingmall.models.Item;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.util.Constants;

public class ProductRepository {
	// delete method 생성
	// 파일명 상수
	public static final String FILE_NAME = Constants.PRODUCT_DATA_FILE;
	
	// 상품 저장
	@SuppressWarnings("unchecked")
	public Item save(Item item) {
		// 기존 상품 목록 조회
		List<Item> items = FileManagement.readFromFile(FILE_NAME);
//		items.addAll((Collection<? extends Item>) Arrays.asList(items));
		// 새 상품 추가
		items.add(item);
		// 파일에 저장
		FileManagement.writeToFile(FILE_NAME, items);
		
		return item;
	}
	
    // 검색 필터 고도화 : 상품명이나 설명 중 어느 하나에라도 검색어가 포함된 상품 검색
	
	
    // 제품 삭제 메서드 추가
    public boolean deleteById(String id) {
		List<Item> items = FileManagement.readFromFile(FILE_NAME);
		
		for (int i=0; i< items.size(); i++) {
			if (items.get(i).getItemID().equals(id)) {
				items.remove(i);
				FileManagement.writeToFile(FILE_NAME, items);
				
				return true; //삭제 성공
			}
		}
    	
    	return false; //id에 해당하는 제품을 찾지 못함
    	
    }
    
    // 제품 수정 메서드 추가 1. 가격 수정
    public boolean correctPriceById(String id, int newPrice) {
		List<Item> items = FileManagement.readFromFile(FILE_NAME);
		
		for(Item item : items) {
			if(item.getItemID().equals(id)) {
				item.setPrice(newPrice);
				FileManagement.writeToFile(FILE_NAME, items);
				return true;
			}
		}
		
    	return false; //id에 해당하는 제품을 찾지 못함
    	
    }

    
    // 제품 수정 메서드 추가 2. 설명 수정
    public boolean correctDescById(String id, String newDesc) {
		List<Item> items = FileManagement.readFromFile(FILE_NAME);
		
		for(Item item : items) {
			if(item.getItemID().equals(id)) {
				item.setDescription(newDesc);
				FileManagement.writeToFile(FILE_NAME, items);
				return true;
			}
		}
		
    	return false; //id에 해당하는 제품을 찾지 못함
    	
    }
    
    


	
	
		
}


// 
