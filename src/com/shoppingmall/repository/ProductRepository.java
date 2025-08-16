package com.shoppingmall.repository;

import java.util.List;

import com.shoppingmall.models.Item;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.util.Constants;

public class ProductRepository {
	
	// 파일명 상수
	public static final String FILE_NAME = Constants.PRODUCT_DATA_FILE;
	
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
	
		
}


// 
