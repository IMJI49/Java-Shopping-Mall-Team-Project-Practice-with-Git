package com.shoppingmall.service;

import java.util.Scanner;

import com.shoppingmall.models.Manager;

public class ManagerService {
	/*
	 * order status : confirm, cancel, shipping
	 * product : add, delete, modify
	 * customer : delete, 회원 검색시 :  이름, 배송지, 전화번호, 상세정보에 id 배송 횟수, 구매 금액 추가
	 *추가 구현시에는 리뷰까지만
	 */
	/*
	 * 상품 재고까지 확인
	 * 1~6번 없애고
	 * 
	 * 1. 주문 관리
	 * 		1. 주문 내역 확인
	 * 		2. 주문 confirm
	 * 		3. 주문 취소
	 * 2. 마이페이지 - customer mypage랑 동일 4,5 감추는것으로
	 * 3. 관리 상품관리 (change 재고 관리 : quantyty int값으로 받아서 증가만)  (5번 전체 상품 보기랑 같음)
	 * 		1. 
	 * 4. 사용자 관리 : 
	 * 		1. 전체 회원 조회 (이름, id, 주소, 폰넘버) - 관리자 나오면 안됩니다
	 * 		2. 회원검색 이름으로 검색
	 *		3. 상세정보는 id 검색 : 비밀번호 제외 모든값 나오게 (주문 내역도 포함)
	 *		4. 회원강제 탈퇴 : id로 탈퇴시키고 사유 작성 및 통보
	 *		5. 돌아가기
	 * 6. 로그아웃
	 * 추가 구현 기능 판매된 아이템 보기 (statistic)
	 */
	private Scanner scanner;
	private Manager manager;
	public ManagerService() {
		scanner = new Scanner(System.in);
	}
	
}

	