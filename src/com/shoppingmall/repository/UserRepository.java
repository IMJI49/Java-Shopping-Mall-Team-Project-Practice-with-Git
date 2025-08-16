package com.shoppingmall.repository;


import java.util.List;

import com.shoppingmall.models.Customer;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.util.Constants;

public class UserRepository {


	// 파일명 상수
	private static final String FILE_NAME = Constants.USER_DATA_FILE;
	
	public UserRepository() {
		
    	initialize();
    	
	}
	
    // 기본 고객 계정 생성(초기화)
	public void initialize() {
		List<Customer> customers = FileManagement.readFromFile(FILE_NAME);
		
		//기본 관리자가 없으면 생성
		if(customers.isEmpty()) {
			
			Customer defaultCustomer = new Customer(
					"고객1", 
					"서울시 중구 중동", 
					"customer1@gmail.com", 
					"customer1", 
					"customer1234", 
					"010-5678-1234"
					);
			customers.add(defaultCustomer);
			FileManagement.writeToFile(FILE_NAME, customers);
			System.out.println("기본 고객 계정이 생성되었습니다.");
		}
	}

	// 사용자 저장
	public Customer save(Customer customer) {
		// 기존 사용자 목록 조회
		List<Customer> customers = FileManagement.readFromFile(FILE_NAME);
		
		// 새 사용자 추가
		customers.add(customer);
		
		// 파일에 저장
		FileManagement.writeToFile(FILE_NAME, customers);
		
		return customer;
	}

	
	// 사용자 존재 여부 확인
	public boolean existsById(String id) {
		
		return findById(id) != null;
	}

	
	// 사용자 조회 (ID로 검색)
	public Object findById(String id) {
		List<Customer> users = FileManagement.readFromFile(FILE_NAME);
		return users.stream()
				.filter(u -> u.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

	// 모든 사용자 데이터 반환 (비밀번호 제외)
	public List<Customer> getAllCustomers() {
	    List<Customer> customers = FileManagement.readFromFile(FILE_NAME);
		
	    //비밀번호만 * 16자로 바꾸기
	    for(Customer customer : customers) {
	    	customer.setPassword("*".repeat(16));
	    	
	    }
	    
		return customers;
	}

	
	
	// 사용자 인증
	public static Customer authenticate(String id, String password) {
		
		List<Customer> customers = FileManagement.readFromFile(FILE_NAME);
		
		Customer foundCustomer = customers.stream()
									.filter(customer -> customer.getId().equals(id) &&
											customer.getPassword().equals(password))
									.findFirst()
									.orElse(null);
		
		return foundCustomer;
	}
	
	
	// 비밀번호 확인 (개인정보 수정 전 인증용)
    public boolean verifyPassword(String id, String password) {
        Customer customer = (Customer) findById(id);
        return customer != null && customer.getPassword().equals(password);
    }
    
    // 이메일 수정
    public boolean updateEmail(String id, String currentPassword, String newEmail) {
        if (!verifyPassword(id, currentPassword)) {
            return false; // 비밀번호 불일치
        }

        List<Customer> customers = FileManagement.readFromFile(FILE_NAME);
        
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                customer.setEmail(newEmail);
                FileManagement.writeToFile(FILE_NAME, customers);
                return true;
            }
        }
        return false;
    }
    
    // 전화번호 수정
    public boolean updatePhone(String id, String currentPassword, String newPhone) {
        if (!verifyPassword(id, currentPassword)) {
            return false; // 비밀번호 불일치
        }

        List<Customer> customers = FileManagement.readFromFile(FILE_NAME);
        
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                customer.setPhone(newPhone);
                FileManagement.writeToFile(FILE_NAME, customers);
                return true;
            }
        }
        return false;
    }
    
    // 주소 수정
    public boolean updateAddress(String id, String currentPassword, String newAddress) {
        if (!verifyPassword(id, currentPassword)) {
            return false; // 비밀번호 불일치
        }

        List<Customer> customers = FileManagement.readFromFile(FILE_NAME);
        
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                customer.setAddress(newAddress);
                FileManagement.writeToFile(FILE_NAME, customers);
                return true;
            }
        }
        return false;
    }
    
    
    // 비밀번호 변경
    public boolean changePassword(String id, String currentPassword, String newPassword) {
        if (!verifyPassword(id, currentPassword)) {
            return false; // 현재 비밀번호 불일치
        }
 
        List<Customer> customers = FileManagement.readFromFile(FILE_NAME);
        
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                customer.setPassword(newPassword);
                FileManagement.writeToFile(FILE_NAME, customers);
                return true;
            }
        }
        return false;
    }
    
    
    // 리뷰 작성 기능
    public void writeReview(String customerId, String itemId, int rating, String reviewText) {
       
        ProductRepository productRepository = new ProductRepository();
        productRepository.addReview(itemId, customerId, rating, reviewText);
        
        System.out.println("리뷰가 성공적으로 작성되었습니다");
    }
    
	
}
