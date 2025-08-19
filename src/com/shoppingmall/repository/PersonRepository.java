package com.shoppingmall.repository;

import java.util.List;

import com.shoppingmall.models.Manager;
import com.shoppingmall.models.Person;
import com.shoppingmall.models.Customer;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.util.Constants;
 
public class PersonRepository {

    // 통합 인증 메서드 - Manager와 Customer 파일 모두 확인
    public String authenticate(String id, String password) {
        // Manager에서 확인
        List<Manager> managers = FileManagement.readFromFile(Constants.MANAGER_DATA_FILE);
        for (Manager manager : managers) {
            if (manager.getId().equals(id) && manager.getPassword().equals(password)) {
                return manager.getRole();
            }
        }

        // Customer에서 확인
        List<Customer> customers = FileManagement.readFromFile(Constants.USER_DATA_FILE);
        for (Customer customer : customers) {
            if (customer.getId().equals(id) && customer.getPassword().equals(password)) {
                return customer.getRole();
            }
        }

        return null; // 인증 실패
    }
    
    
    
    // 사용자 삭제 메서드 - Customer 계정용 // Manager의 계정은 삭제 불가능
    public boolean deleteById(String id) {
        List<Customer> customers = FileManagement.readFromFile(Constants.USER_DATA_FILE);

        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equals(id)) {
                customers.remove(i);
                FileManagement.writeToFile(Constants.USER_DATA_FILE, customers);
                System.out.println("삭제 성공: " + id);
                return true; // 삭제 성공
            }
        }
        
        System.out.println("다음 사용자를 찾지 못했습니다: " + id);
        return false; // id에 해당하는 사용자를 찾지 못함
    }
    
	// 사용자 존재 여부 확인
	public boolean existsById(String id) {
		
		return findById(id) != null;
	}
    
    
    // ID로 Person 찾기 (개인정보 수정용) - Manager, Customer 모두 가능
    private Person findById(String id) {
        // Manager에서 먼저 찾기
        List<Manager> managers = FileManagement.readFromFile(Constants.MANAGER_DATA_FILE);
        Manager manager = managers.stream().filter(i -> i.getId().equals(id))
				        				 .findFirst()
				        				 .orElse(null);
        if(manager != null) {
        	return manager;
        }
        
        // Customer에서 찾기
        List<Customer> customers = FileManagement.readFromFile(Constants.USER_DATA_FILE);
        return customers.stream().filter(i -> i.getId().equals(id))
											  .findFirst()
											  .orElse(null);

        }
    
    
    // 비밀번호 확인 (개인정보 수정 전 인증 용) - Manager, Customer 모두 가능
    public boolean verifyPassword(String id, String password) {
    	Person person = findById(id);
    	return (person != null) && (person.getPassword().equals(password)); 
    }

    // 개인정보 수정 메서드 1. 비밀번호 수정 - Manager, Customer 모두 가능
    public boolean updatePassword(String id, String currentPassword, String newPassword) {
    	if(!verifyPassword(id, currentPassword)) {
    		System.out.println("ID와 비밀번호가 일치하지 않습니다.");
    		return false;	// 인증 실패
    	}
    	
    	//Manager인지 확인 후 수정
    	List<Manager> managers = FileManagement.readFromFile(Constants.MANAGER_DATA_FILE);
    	Manager manager = managers.stream().filter(i ->i.getId().equals(id))
    										.findFirst()
    										.orElse(null);
    	if (manager != null) {
    	    manager.setPassword(newPassword);
    	    FileManagement.writeToFile(Constants.MANAGER_DATA_FILE, managers);

    	    System.out.println("성공했습니다 : 관리자 비밀번호 수정");
    	    return true;
    	}
    	
    	//Customer인지 확인 후 수정
    	List<Customer> customers = FileManagement.readFromFile(Constants.USER_DATA_FILE);
    	Customer customer = customers.stream().filter(i ->i.getId().equals(id))
    										.findFirst()
    										.orElse(null);
    	if (customer != null) {
    		customer.setPassword(newPassword);
    	    FileManagement.writeToFile(Constants.USER_DATA_FILE, customers);
    	    
    	    System.out.println("성공했습니다 : 사용자 비밀번호 수정");
    	    return true;
    	}
    	
    	return false;
    	
	}
    
    // 개인정보 수정 메서드 2. 이메일 수정 - Manager, Customer 모두 가능
    public boolean updateEmail(String id, String currentPassword, String newEmail) {
    	if(!verifyPassword(id, currentPassword)) {
    		System.out.println("ID와 비밀번호가 일치하지 않습니다.");
    		return false;	// 인증 실패
    	}
    	
    	//Manager인지 확인 후 수정
    	List<Manager> managers = FileManagement.readFromFile(Constants.MANAGER_DATA_FILE);
    	Manager manager = managers.stream().filter(i ->i.getId().equals(id))
    										.findFirst()
    										.orElse(null);
    	if (manager != null) {
    	    manager.setEmail(newEmail);
    	    FileManagement.writeToFile(Constants.MANAGER_DATA_FILE, managers);

    	    System.out.println("성공했습니다 : 관리자 이메일 수정");
    	    return true;
    	}
    	
    	//Customer인지 확인 후 수정
    	List<Customer> customers = FileManagement.readFromFile(Constants.USER_DATA_FILE);
    	Customer customer = customers.stream().filter(i ->i.getId().equals(id))
    										.findFirst()
    										.orElse(null);
    	if (customer != null) {
    		customer.setEmail(newEmail);
    	    FileManagement.writeToFile(Constants.USER_DATA_FILE, customers);
    	    
    	    System.out.println("성공했습니다 : 사용자 이메일 수정");
    	    return true;
    	}
    	
    	return false;
    	
	}
    
    
    // 개인정보 수정 메서드 3. 주소 수정 - Manager, Customer 모두 가능
    public boolean updateAddress(String id, String currentPassword, String newAddress) {
    	if(!verifyPassword(id, currentPassword)) {
    		System.out.println("ID와 비밀번호가 일치하지 않습니다.");
    		return false;	// 인증 실패
    	}
    	
    	//Manager인지 확인 후 수정
    	List<Manager> managers = FileManagement.readFromFile(Constants.MANAGER_DATA_FILE);
    	Manager manager = managers.stream().filter(i ->i.getId().equals(id))
    										.findFirst()
    										.orElse(null);
    	if (manager != null) {
    	    manager.setAddress(newAddress);
    	    FileManagement.writeToFile(Constants.MANAGER_DATA_FILE, managers);

    	    System.out.println("성공했습니다 : 관리자 주소 수정");
    	    return true;
    	}
    	
    	//Customer인지 확인 후 수정
    	List<Customer> customers = FileManagement.readFromFile(Constants.USER_DATA_FILE);
    	Customer customer = customers.stream().filter(i ->i.getId().equals(id))
    										.findFirst()
    										.orElse(null);
    	if (customer != null) {
    		customer.setAddress(newAddress);
    	    FileManagement.writeToFile(Constants.USER_DATA_FILE, customers);
    	    
    	    System.out.println("성공했습니다 : 사용자 주소 수정");
    	    return true;
    	}
    	
    	return false;
    	
	}
    	
    // 개인정보 수정 메서드 4. 전화번호 수정 - Manager, Customer 모두 가능
    public boolean updatePhoneNumber(String id, String currentPassword, String newPhoneNumber) {
    	if(!verifyPassword(id, currentPassword)) {
    		System.out.println("ID와 비밀번호가 일치하지 않습니다.");
    		return false;	// 인증 실패
    	}
    	
    	//Manager인지 확인 후 수정
    	List<Manager> managers = FileManagement.readFromFile(Constants.MANAGER_DATA_FILE);
    	Manager manager = managers.stream().filter(i ->i.getId().equals(id))
    										.findFirst()
    										.orElse(null);
    	if (manager != null) {
    	    manager.setPhoneNumber(newPhoneNumber);
    	    FileManagement.writeToFile(Constants.MANAGER_DATA_FILE, managers);

    	    System.out.println("성공했습니다 : 관리자 전화번호 수정");
    	    return true;
    	}
    	
    	//Customer인지 확인 후 수정
    	List<Customer> customers = FileManagement.readFromFile(Constants.USER_DATA_FILE);
    	Customer customer = customers.stream().filter(i ->i.getId().equals(id))
    										.findFirst()
    										.orElse(null);
    	if (customer != null) {
    		customer.setPhoneNumber(newPhoneNumber);
    	    FileManagement.writeToFile(Constants.USER_DATA_FILE, customers);
    	    
    	    System.out.println("성공했습니다 : 사용자 전화번호 수정");
    	    return true;
    	}
    	
    	return false;
    	
	}

}


    
    

