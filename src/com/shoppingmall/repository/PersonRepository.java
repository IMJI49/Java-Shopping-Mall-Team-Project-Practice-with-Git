package com.shoppingmall.repository;

import java.util.List;

import com.shoppingmall.models.Manager;
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
}
