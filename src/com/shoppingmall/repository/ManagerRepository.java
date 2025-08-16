package com.shoppingmall.repository;

import java.util.List;

import com.shoppingmall.models.Manager;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.util.Constants;
 
public class ManagerRepository {
 
	
	// 파일명 상수
    private static final String FILE_NAME = Constants.MANAGER_DATA_FILE;
  
    public ManagerRepository() {
	
    	initialize();
    
    }

    // 기본 관리자 계정 생성(초기화)
	public void initialize() {
		List<Manager> managers = FileManagement.readFromFile(FILE_NAME);
		
		//기본 관리자가 없으면 생성
		if(managers.isEmpty()) {
			
			Manager defaultManager = new Manager(
					"매니저1",
					"서울시 마포구 마포동",
					"manager1@gmail.com",
					"manager1",
					"manager1234",
					"010-1234-5647"
					);
		managers.add(defaultManager);
		FileManagement.writeToFile(FILE_NAME, managers);
		System.out.println("기본 관리자 계정이 생성되었습니다.");
		}
		
	}

	// 관리자 인증
	public Manager authenticate(String id, String password) {
		List<Manager> managers = FileManagement.readFromFile(FILE_NAME);
		
		Manager foundManager = managers.stream()
									.filter(manager -> manager.getId().equals(id) &&
													 manager.getPassword().equals(password))
									.findFirst()
									.orElse(null);
		
		return foundManager;
	}    
	
	// 관리자 저장
		public Manager save(Manager manager) {
			// 기존 사용자 목록 조회
			List<Manager> managers = FileManagement.readFromFile(FILE_NAME);
			
			// 새 사용자 추가
			managers.add(manager);
			
			// 파일에 저장
			FileManagement.writeToFile(FILE_NAME, managers);
			
			return manager;
		}

		
//		// 사용자 존재 여부 확인
//		public boolean existsById(String id) {
//			
//			return findById(id) != null;
//		}

		
//		// ID로 관리자 조회
//		public Object findById(String id) {
//			List<Manager> managers = FileManagement.readFromFile(FILE_NAME);
//			
//			return managers.stream()
//					.filter(u -> u.getId().equals(id))
//					.findFirst()
//					.orElse(null);
//		}

//		// 모든 관리자 데이터 반환
//		public List<Manager> getAllManagers() {
//			return FileManagement.readFromFile(FILE_NAME);
//		}
	
	
}
