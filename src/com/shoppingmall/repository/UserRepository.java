package com.shoppingmall.repository;


import java.util.List;

import com.shoppingmall.models.Customer;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.util.Constants;

public class UserRepository {

	// delete method 생성
	// 파일명 상수
	public static final String FILE_NAME = Constants.USER_DATA_FILE;

	// 사용자 저장
			public Customer save(Customer customer) {
				// 기존 사용자 목록 조회
				List<Customer> customers = FileManagement.readFromFile(UserRepository.FILE_NAME);
				
				// 새 사용자 추가
				customers.add(customer);
				
				// 파일에 저장
				FileManagement.writeToFile(UserRepository.FILE_NAME, customers);
				
				return customer;
			}

			
			// 사용자 존재 여부 확인
			public boolean existsById(String id) {
				
				return findById(id) != null;
			}

			
			// 사용자 조회 (ID로 검색)
			public Customer findById(String id) {
				List<Customer> users = FileManagement.readFromFile(UserRepository.FILE_NAME);
				return users.stream()
						.filter(u -> u.getId().equals(id))
						.findFirst()
						.orElse(null);
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
        Customer customer = findById(id);
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
    
    // 사용자 삭제 메서드 추가
    public boolean deleteById(String id) {
		List<Customer> customers = FileManagement.readFromFile(FILE_NAME);
		
		for (int i=0; i< customers.size(); i++) {
			if (customers.get(i).getId().equals(id)) {
				customers.remove(i);
				FileManagement.writeToFile(FILE_NAME, customers);
				
				return true; //삭제 성공
			}
		}
    	
    	return false; //id에 해당하는 고객을 찾지 못함
    	
    }
    

	
}
