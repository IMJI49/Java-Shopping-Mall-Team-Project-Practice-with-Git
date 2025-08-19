package com.shoppingmall.repository;

import java.util.List;

import com.shoppingmall.models.Person;
import com.shoppingmall.models.Manager;
import com.shoppingmall.models.Customer;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.util.Constants;

public class PersonRepository {

    public PersonRepository() {
        initialize();
    }

    // 기본 관리자 계정 생성(초기화)
    public void initialize() {
        List<Manager> managers = FileManagement.readFromFile(Constants.MANAGER_DATA_FILE);

        // 기본 관리자가 없으면 생성
        if (managers.isEmpty()) {
            Manager defaultManager = new Manager(
                "매니저1",
                "서울시 마포구 마포동",
                "manager1@gmail.com",
                "manager1",
                "manager1234",
                "010-1234-5647"
            );
            managers.add(defaultManager);
            FileManagement.writeToFile(Constants.MANAGER_DATA_FILE, managers);
            System.out.println("기본 관리자 계정이 생성되었습니다.");
        }
    }

    // 통합 인증 메서드 - Manager와 Customer 파일 모두 확인
    public Person authenticate(String id, String password) {
        // Manager에서 확인
        List<Manager> managers = FileManagement.readFromFile(Constants.MANAGER_DATA_FILE);
        for (Manager manager : managers) {
            if (manager.getId().equals(id) && manager.getPassword().equals(password)) {
                return manager;
            }
        }

        // Customer에서 확인
        List<Customer> customers = FileManagement.readFromFile(Constants.USER_DATA_FILE);
        for (Customer customer : customers) {
            if (customer.getId().equals(id) && customer.getPassword().equals(password)) {
                return customer;
            }
        }

        return null; // 인증 실패
    }

    // 역할 확인 메서드 (id 기반)
    public String getRole(String id) {
        Person person = findPersonById(id);
        if (person == null) return null;

        if (person instanceof Manager manager) {
            return manager.getRole();   // "관리자"
        } else if (person instanceof Customer customer) {
            return customer.getRole(); // "이용자"
        }

        return null;
    }

    // ID로 Person 조회 (Manager와 Customer 파일 모두 확인)
    private Person findPersonById(String id) {
        // Manager에서 확인
        List<Manager> managers = FileManagement.readFromFile(Constants.MANAGER_DATA_FILE);
        for (Manager manager : managers) {
            if (manager.getId().equals(id)) {
                return manager;
            }
        }

        // Customer에서 확인
        List<Customer> customers = FileManagement.readFromFile(Constants.USER_DATA_FILE);
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }

        return null;
    }
}
