package com.shoppingmall.controller;

import java.util.Scanner;

import com.shoppingmall.service.ManagerService;
import com.shoppingmall.service.UserService;

public class MainController {
	private Scanner scanner;
	private ManagerService managerService;
	private UserService userService;
	private boolean isManager = false;	//임시 관리자모드
	
	public MainController() {
		this.scanner = new Scanner(System.in);
		managerService = new ManagerService();
		userService = new UserService();
	}
	
	public void start() {
		showMainMenu();
	}
	
	//메인메뉴
	private void showMainMenu() {
		while(true) {
			// 메인메뉴
			System.out.println("╔════════════════════════════════════════════╗");
			System.out.println("║     🛍️  Java Shopping Mall                 ║");
			System.out.println("╚════════════════════════════════════════════╝");
			System.out.println("1.  회원가입");
			System.out.println("2. 로그인");
			System.out.println("3. 상품 둘러보기");
			System.out.println("0. 프로그램 종료");
			System.out.print("메뉴를 선택하세요: _");
			
			String menu = scanner.nextLine();
			switch(menu) {
				case "1":
					break;
				case "2":
					if(isManager) {
						// 관리자 메뉴
						while(true) {
							System.out.println("╔════════════════════════════════════════════╗");
							System.out.println("║     🛍️  Java Shopping Mall                 ║");
							System.out.println("║      [관리자 모드] 환영합니다!                   ║");
							System.out.println("╚════════════════════════════════════════════╝");
							System.out.println("1. 주문내역 확인");
							System.out.println("2. 상품 취소 관리");
							System.out.println("3. 마이페이지");
							System.out.println("4. [관리] 상품 관리");
							System.out.println("5. [관리] 사용자 관리");
							System.out.println("0. 로그아웃");
							System.out.print("메뉴를 선택하세요: _");
							
							menu = scanner.nextLine();
							if(menu.equals("1")) {
								
							}else if(menu.equals("2")) {
								
							}else if(menu.equals("3")) {
								// 매니저 마이페이지
								while(true) {
									System.out.println("┌────────────────────────────────────┐");
									System.out.println("│    👤[관리자 모드] 마이페이지            │");
									System.out.println("├────────────────────────────────────┤");
									System.out.println("│  1. 내 정보 조회                      │");
									System.out.println("│  2. 비밀번호 변경                      │");
									System.out.println("│  3. 개인정보 수정                     │");
									System.out.println("│  0. 돌아가기                         │");
									System.out.println("└────────────────────────────────────┘");
									System.out.print("메뉴를 선택하세요: _");
									
									menu = scanner.nextLine();
									if(menu.equals("1")) {
										
									}else if (menu.equals("2")) {
										
									}else if (menu.equals("3")) {
										
									}else if (menu.equals("0")) {
										break;
									}else {
										System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
									}
								}
							}else if(menu.equals("4")) {
								// 관리자 상품 관리 메뉴
								while(true) {
									System.out.println("┌────────────────────────────────────┐");
									System.out.println("│      📦 [관리자] 상품 관리             │");
									System.out.println("├────────────────────────────────────┤");
									System.out.println("│  1. 상품 등록                        │");
									System.out.println("│  2. 상품 수정                        │");
									System.out.println("│  3. 상품 삭제                        │");
									System.out.println("│  4. 재고 관리                        │");
									System.out.println("│  5. 상품 목록 조회                    │");
									System.out.println("│  0. 돌아가기                         │");
									System.out.println("└────────────────────────────────────┘");
									System.out.print("메뉴를 선택하세요: _");
									
									menu = scanner.nextLine();
									if(menu.equals("1")) {
										
									}else if (menu.equals("2")) {
										
									}else if (menu.equals("3")) {
										
									}else if (menu.equals("4")) {
										
									}else if (menu.equals("5")) {
										
									}else if (menu.equals("0")) {
										break;
									}else {
										System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
									}
								}
							}else if(menu.equals("5")) {
								// 관리자 사용자 관리 메뉴
								while(true) {
									System.out.println("┌────────────────────────────────────┐");
									System.out.println("│      👥 [관리자] 사용자 관리            │");
									System.out.println("├────────────────────────────────────┤");
									System.out.println("│  1. 전체 회원 조회                     │");
									System.out.println("│  2. 회원 검색                        │");
									System.out.println("│  3. 회원 상세 정보                    │");
									System.out.println("│  4. 회원 강제 탈퇴                    │");
									System.out.println("│  0. 돌아가기                         │");
									System.out.println("└────────────────────────────────────┘");
									System.out.print("메뉴를 선택하세요: _");
									
									menu = scanner.nextLine();
									if(menu.equals("1")) {
										
									}else if (menu.equals("2")) {
										
									}else if (menu.equals("3")) {
										
									}else if (menu.equals("4")) {
										
									}else if (menu.equals("0")) {
										break;
									}else {
										System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
									}
								}
							}else if (menu.equals("0")) {
								break;
							}else {
								System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
							}
						}
					}
					else {
						// 일반 사용자 메뉴
						while(true) {
							System.out.println("╔════════════════════════════════════════════╗");
							System.out.println("║     🛍️  Java Shopping Mall                 ║");
							System.out.printf("║       환영합니다, [사용자명]님!                 ║\n");
							System.out.println("╚════════════════════════════════════════════╝");
							System.out.println("1. 상품 둘러보기");
							System.out.println("2. 상품 검색");
							System.out.println("3. 장바구니 관리");
							System.out.println("4. 주문하기");
							System.out.println("5. 주문내역");
							System.out.println("6. 마이페이지");
							System.out.println("0. 로그아웃");
							System.out.print("\n메뉴를 선택하세요: _");
							
							menu = scanner.nextLine();
							if(menu.equals("1")) {
								// 상품 둘러보기 메뉴
								while(true) {
									System.out.println("┌────────────────────────────────────┐");
									System.out.println("│         🛍️ 상품 둘러보기               │");
									System.out.println("├────────────────────────────────────┤");
									System.out.println("│  1. 전체 상품 보기                    │");
									System.out.println("│  2. 카테고리별 보기                    │");
									System.out.println("│  3. 가격대별 보기                     │");
									System.out.println("│  4. 베스트셀러                       │");
									System.out.println("│  5. 신상품                          │");
									System.out.println("│  6. 상품 상세보기                     │");
									System.out.println("|  7. 상품 리뷰하기                     |");
									System.out.println("│  0. 돌아가기                         │");
									System.out.println("└────────────────────────────────────┘");
									System.out.print("메뉴를 선택하세요: _");
									
									menu = scanner.nextLine();
									if(menu.equals("1")) {
										
									}else if (menu.equals("2")) {
										
									}else if (menu.equals("3")) {
										
									}else if (menu.equals("4")) {
										
									}else if (menu.equals("5")) {
										
									}else if (menu.equals("6")) {
										
									}else if (menu.equals("7")) {
										System.out.print("리뷰할 상품의 아이디를 입력하세요 : _");
										String itmeId = scanner.nextLine();
										userService.reviewing(itmeId);
									}else if (menu.equals("0")) {
										break;
									}else {
										System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
									}
								}
							}else if (menu.equals("2")) {
								
							}else if (menu.equals("3")) {
								// 장바구니 관리
								while(true) {
									System.out.println("┌────────────────────────────────────┐");
									System.out.println("│         🛒 장바구니 관리               │");
									System.out.println("├────────────────────────────────────┤");
									System.out.println("│  1. 장바구니 조회                      │");
									System.out.println("│  2. 상품 추가                        │");
									System.out.println("│  3. 수량 변경                        │");
									System.out.println("│  4. 상품 삭제                        │");
									System.out.println("│  5. 장바구니 비우기                    │");
									System.out.println("│  0. 돌아가기                         │");
									System.out.println("└────────────────────────────────────┘");
									System.out.print("메뉴를 선택하세요: _");
									
									menu = scanner.nextLine();
									if(menu.equals("1")) {
										
									}else if (menu.equals("2")) {
										
									}else if (menu.equals("3")) {
										
									}else if (menu.equals("4")) {
										
									}else if (menu.equals("5")) {
										
									}else if (menu.equals("0")) {
										break;
									}else {
										System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
									}
								}
							}else if (menu.equals("4")) {
								
							}else if (menu.equals("5")) {
								
							}else if (menu.equals("6")) {
								// 일반 사용자 마이페이지
								while(true) {
									System.out.println("┌────────────────────────────────────┐");
									System.out.println("│         👤 마이페이지                 │");
									System.out.println("├────────────────────────────────────┤");
									System.out.println("│  1. 내 정보 조회                      │");
									System.out.println("│  2. 비밀번호 변경                      │");
									System.out.println("│  3. 개인정보 수정                     │");
									System.out.println("│  4. 주문 내역 조회                    │");
									System.out.println("│  5. 회원 탈퇴                        │");
									System.out.println("│  0. 돌아가기                         │");
									System.out.println("└────────────────────────────────────┘");
									System.out.print("메뉴를 선택하세요: _");
									
									menu = scanner.nextLine();
									if(menu.equals("1")) {
										
									}else if (menu.equals("2")) {
										
									}else if (menu.equals("3")) {
										
									}else if (menu.equals("4")) {
										
									}else if (menu.equals("5")) {
										
									}else if (menu.equals("0")) {
										break;
									}else {
										System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
									}
								}
							}else if (menu.equals("0")) {
								break;
							}else {
								System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
							}
						}
					}
					break;
				case "3":
					//상품 둘러보기 메뉴
					while(true) {
						System.out.println("┌────────────────────────────────────┐");
						System.out.println("│         🛍️ 상품 둘러보기               │");
						System.out.println("├────────────────────────────────────┤");
						System.out.println("│  1. 전체 상품 보기                    │");
						System.out.println("│  2. 카테고리별 보기                    │");
						System.out.println("│  3. 가격대별 보기                     │");
						System.out.println("│  4. 베스트셀러                       │");
						System.out.println("│  5. 신상품                          │");
						System.out.println("│  6. 상품 상세보기                     │");
						System.out.println("│  0. 돌아가기                         │");
						System.out.println("└────────────────────────────────────┘");
						System.out.print("메뉴를 선택하세요: _");
						
						menu = scanner.nextLine();
						if(menu.equals("1")) {
							
						}else if (menu.equals("2")) {
							
						}else if (menu.equals("3")) {
							
						}else if (menu.equals("4")) {
							
						}else if (menu.equals("5")) {
							
						}else if (menu.equals("6")) {
							
						}else if (menu.equals("7")) {
							
						}else if (menu.equals("0")) {
							break;
						}else {
							System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
						}
					}
					break;
				case "0":
					return;
				default:
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					break;
			}
		}
	}
		
}

	
