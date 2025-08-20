package com.shoppingmall.controller;

import java.util.ArrayList;
import java.util.Scanner;

import com.shoppingmall.exception.ShoppingMallException;
import com.shoppingmall.models.CartItem;
import com.shoppingmall.models.Customer;
import com.shoppingmall.models.Item;
import com.shoppingmall.models.Manager;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.repository.ProductRepository;
import com.shoppingmall.service.ManagerService;
import com.shoppingmall.service.UserService;
import com.shoppingmall.util.Constants;
import com.shoppingmall.util.ValidationUtils;

public class MainController {
	private Scanner scanner;
	private ManagerService managerService;
	private UserService userService;
	private ProductRepository productRepository;
	
	public MainController() {
		this.scanner = new Scanner(System.in);
		managerService = new ManagerService("Java Shopping Mall");
		userService = new UserService("Java Shopping Mall");
		productRepository = new ProductRepository();
	}

	public void start() throws ShoppingMallException {
		showMainMenu();
	}
	
	//메인메뉴
	private void showMainMenu() throws ShoppingMallException {
		Customer customer = null;
		Manager manager = null;
		while(true) {
			// 메인메뉴
			System.out.println("╔════════════════════════════════════════════╗");
			System.out.println("║     🛍️  "+userService.getMallName()+"                 ║");
			System.out.println("╚════════════════════════════════════════════╝");
			System.out.println("1.  회원가입");
			System.out.println("2. 로그인");
			System.out.println("3. 상품 둘러보기");
			System.out.println("0. 프로그램 종료");
			System.out.print("메뉴를 선택하세요: _");
			
			String menu = scanner.nextLine();
			switch(menu) {
				case "1":
					//회원가입
					break;
				case "2":
					System.out.println("\n===========   로그인   =============");
					System.out.println("아이디를 입력해 주세요");
					String id = scanner.nextLine();
					System.out.println("패스워드를 입력해 주세요");
					String password = scanner.nextLine();
					// userRosi, managerrepo valid
					// getrole if문
					
					System.out.println("로그인 되었습니다.");
					System.out.println("====================================\n");
					//로그인 할 때 아이디가 admin이면 관리자 모드로 로그인
					if(id.equals("admin")) {
						// 관리자 로그인 메뉴
						while(true) {
							System.out.println("╔════════════════════════════════════════════╗");
							System.out.println("║     🛍️  "+managerService.getMallName()+"                 ║");
							System.out.println("║      [관리자 모드] 환영합니다!                   ║");
							System.out.println("╚════════════════════════════════════════════╝");
							System.out.println("1. 주문 관리");
							System.out.println("2. 마이페이지");
							System.out.println("3. [관리] 상품 관리");
							System.out.println("4. [관리] 사용자 관리");
							System.out.println("0. 로그아웃");
							System.out.print("메뉴를 선택하세요: _");
							
							menu = scanner.nextLine();
							if(menu.equals("1")) {
								while(true) {
									System.out.println("┌────────────────────────────────────┐");
									System.out.println("│   📦[관리자 모드] 주문 관리             │");
									System.out.println("├────────────────────────────────────┤");
									System.out.println("│  1. 주문 내역 확인                    │");
									System.out.println("│  2. 주문 confirm                    │");
									System.out.println("│  3. 주문 취소                        │");
									System.out.println("│  0. 돌아가기                         │");
									System.out.println("└────────────────────────────────────┘");
									System.out.print("메뉴를 선택하세요: _");
									
									menu = scanner.nextLine();
									if(menu.equals("1")) {
										System.out.println("\n========   주문 내역   =========");
										
										System.out.println("================================\n");
									}else if (menu.equals("2")) {
										System.out.println("\n======   주문 confirm   =======");
										
										System.out.println("================================\n");
									}else if (menu.equals("3")) {
										System.out.println("\n=======   주문 취소   =======");
										
										System.out.println("==============================");
									}else if (menu.equals("0")) {
										break;
									}else {
										System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
									}
								}
							}else if (menu.equals("2")) {
								// 관리자 마이페이지
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
										//내 정보 조회
										System.out.println("\n========  내 정보 조회  ==========");
										
										System.out.println("===============================\n");
									}else if (menu.equals("2")) {
										System.out.println("\n======== 비밀번호 변경 ==========");
										System.out.print("변경할 비밀번호를 입력해주세요: _");
										String changePassword = scanner.nextLine();
										
										System.out.println("변경이 완료되었습니다.");
										System.out.println("====================================\n");
									}else if (menu.equals("3")) {
										System.out.println("\n======== 개인정보 수정 ==========");
										System.out.print("변경할 주소를 입력하세요: _");
										String address = scanner.nextLine();
										System.out.print("변경할 이메일을 입력하세요: _");
										String email = scanner.nextLine();
										System.out.print("변경할 전화번호를 입력하세요: _");
										String phoneNumber = scanner.nextLine();
										
										System.out.println("변경이 완료되었습니다.");
										System.out.println("====================================\n");
									}else if (menu.equals("0")) {
										break;
									}else {
										System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
									}
								}
							}else if (menu.equals("3")) {
								itemController(scanner);
							}else if (menu.equals("4")) {
								// 관리자 사용자 관리 메뉴
								while(true) {
									System.out.println("┌────────────────────────────────────┐");
									System.out.println("│      👥 [관리자] 사용자 관리            │");
									System.out.println("├────────────────────────────────────┤");
									System.out.println("│  1. 전체 회원 조회                     │");
									System.out.println("│  2. 회원 검색(이름으로 검색)            │");
									System.out.println("│  3. 회원 상세 정보(id로 검색)           │");
									System.out.println("│  4. 회원 강제 탈퇴                    │");
									System.out.println("│  0. 돌아가기                         │");
									System.out.println("└────────────────────────────────────┘");
									System.out.print("메뉴를 선택하세요: _");
									
									menu = scanner.nextLine();
									if(menu.equals("1")) {
										System.out.println("\n=======  전체 회원 조회  =========");
										
										System.out.println("=================================\n");
									}else if (menu.equals("2")) {
										System.out.println("\n========   회원 검색   =========");
										System.out.print("검색할 회원의 이름을 입력하세요: _");
										String name = scanner.nextLine();
										
										System.out.println("==============================\n");
									}else if (menu.equals("3")) {
										System.out.println("\n========  회원 상세 정보  =========");
										System.out.print("정보를 확인할 회원의 id를 입력하세요: _");
										String searchId = scanner.nextLine();
										
										System.out.println("==================================");
									}else if (menu.equals("4")) {
										System.out.println("\n========   회원 강제 탈퇴   ========");
										System.out.print("탈퇴시킬 회원의 id를 입력해주세요: _");
										String leaveId = scanner.nextLine();
										
										System.out.println("탈퇴시켰습니다.");
										System.out.println("====================================");
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
					}else {
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
									/*
									 * 상품 둘러보기
									 * 리뷰하기는 마이페이지에서
									 */
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
										System.out.println("\n======= 전체 상품 보기 ==========");
										
										System.out.println("================================\n");
									}else if (menu.equals("2")) {
										//카테고리별 보기
										System.out.println("\n======= 카테고리별 보기 ========");
										System.out.print("카테고리를 입력해 주세요");
										String category = scanner.nextLine();
										
										System.out.println("================================\n");
									}else if (menu.equals("3")) {
										//가격대별 보기
										System.out.println("\n======== 가격대별 보기 ===========");
										System.out.print("번호를 선택해 주세요.(1. 3만원 미만, 2. 3-10만원, 3. 10-50만원,4. 50만원 이상)");
										int number = scanner.nextInt();
										scanner.nextLine();
										
										System.out.println("=================================\n");
									}else if (menu.equals("4")) {
										//베스트셀러
										System.out.println("\n========  베스트셀러  ==========");
										userService.findBestSeller();
										System.out.println("===============================\n");
									}else if (menu.equals("5")) {
										// 신상품은 등록 3일 이내 제품
										System.out.println("\n======== 신상품 보기 ============");
										
										System.out.println("================================\n");
									}else if (menu.equals("6")) {
										//상품 상세보기
										System.out.println("\n==========  상품 상세보기  ===========");
										System.out.print("상품 이름을 입력해 주세요");
										String itemname = scanner.nextLine();
										userService.showItemDetails(itemname);
										
										System.out.println("=====================================\n");
									}else if (menu.equals("0")) {
										break;
									}else {
										System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
									}
								}
							}else if (menu.equals("2")) {
								//상품 검색
								System.out.print("검색할 상품의 상품명을 입력해주세요: _");
								String name = scanner.nextLine();
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

										System.out.println("\n=========== 장바구니 조회 ============");
										ArrayList<CartItem> cartItems = userService.getCarts().get(id);
										if(cartItems == null || cartItems.isEmpty()) {
											System.out.println("장바구니가 비어 있습니다.");
										} else {
											System.out.println("[장바구니 현재 목록]");
											for(CartItem ci : cartItems) {
												System.out.println(ci);
											}
										}
										System.out.println("=====================================\n");
									}else if (menu.equals("2")) {
										System.out.println("\n=========  상품 추가 =============");
										System.out.print("추가할 상품의 이름을 입력해주세요: _");
										String name = scanner.nextLine();
										Item item = userService.getItembyName(name);
										if(item == null) {
											System.out.println("존재하지 않는 상품입니다.");
											continue;
                      }
										System.out.print("추가 수량을 입력하세요: ");
										int qty;
										try {
											qty = Integer.parseInt(scanner.nextLine());
										} catch(Exception e) {
											System.out.println("올바른 수량을 입력해주세요."); continue;
										}
										try {
											userService.getCarts().putIfAbsent(id, new ArrayList<CartItem>());
											userService.getCarts().get(id).add(new CartItem(item, qty));
											System.out.println("장바구니에 상품이 추가되었습니다.");
										} catch(Exception e) {
											System.out.println("추가 실패: " + e.getMessage());
										}
										System.out.println("================================\n");
									}else if (menu.equals("3")) {
										System.out.println("\n===========  수량 변경  ===============");
										System.out.print("변경할 수량을 입력해주세요: _");
										String sAmount = scanner.nextLine();
										int amount = Integer.parseInt(sAmount);
										ArrayList<CartItem> cartItems = userService.getCarts().get(id);
										if(cartItems == null || cartItems.isEmpty()) {
											System.out.println("장바구니가 비어 있습니다."); continue;
										}
										System.out.print("수량을 변경할 상품 이름: ");
										String targetName = scanner.nextLine();
										boolean found = false;
										for(CartItem ci : cartItems) {
											if(ci.getItem().getName().equals(targetName)) {
												System.out.print("새 수량을 입력하세요: ");
												int newQty;
												try {
													newQty = Integer.parseInt(scanner.nextLine());
													if(newQty <= 0) {
														System.out.println("수량은 1 이상이어야 합니다."); break;
													}
													// 수량 변경(간단 예시: 기존 객체 대체)
												 cartItems.remove(ci);
												 cartItems.add(new CartItem(ci.getItem(), newQty));
												 System.out.println("수량이 변경되었습니다.");
												} catch(Exception e) {
													System.out.println("변경 실패: " + e.getMessage());
												}
												found = true; break;
											}
										}
										if(!found) System.out.println("장바구니에 해당 상품이 없습니다.");
										System.out.println("=======================================\n");
									}else if (menu.equals("4")) {
										System.out.println("\n===========  상품 삭제  ===============");
										System.out.print("삭제할 상품의 상품명을 입력해주세요: _");
										String name = scanner.nextLine();
										
										System.out.println("성공적으로 삭제되었습니다!");
										System.out.println("======================================\n");
									}else if (menu.equals("5")) {
										System.out.println("\n============  장바구니 비우기  =============");
										ArrayList<CartItem> cartItems = userService.getCarts().get(id);
										if(cartItems == null || cartItems.isEmpty()) {
											System.out.println("장바구니가 비어 있습니다."); continue;
										}
										System.out.print("삭제할 상품 이름: ");
										String delName = scanner.nextLine();
										boolean removed = cartItems.removeIf(ci -> ci.getItem().getName().equals(delName));
										if(removed) {
											System.out.println("장바구니에서 상품이 삭제되었습니다.");
										} else {
											System.out.println("장바구니에 해당 상품이 없습니다.");
										}
										
									} else if(menu.equals("5")) {
										ArrayList<CartItem> cartItems = userService.getCarts().get(customer.getId());
										if(cartItems != null) cartItems.clear();
										System.out.println("장바구니가 비워졌습니다.");
										System.out.println("=========================================\n");
									}else if (menu.equals("0")) {

										break;
									} else {
										System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
									}

								}
							}else if (menu.equals("4")) {
								//주문하기
								System.out.println("\n===============  주문하기 ================");
								
								System.out.println("==========================================\n");
							}else if (menu.equals("5")) {
								System.out.println("\n=============  주문내역  ===============");
								
								System.out.println("========================================\n");
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
										System.out.println("\n======== 내 정보 조회 ========");
										
										System.out.println("==============================\n");
									}else if (menu.equals("2")) {
										System.out.println("\n======== 비밀번호 변경  ========");
										System.out.print("변경할 비밀번호를 입력해주세요: _");
										String changePassword = scanner.nextLine();
										
										System.out.println("비밀번호가 변경되었습니다!");
										System.out.println("================================\n");
									}else if (menu.equals("3")) {
										System.out.println("\n======== 개인정보를 변경합니다 ==========");
										System.out.print("변경할 주소를 입력하세요: _");
										String address = scanner.nextLine();
										System.out.print("변경할 이메일을 입력하세요: _");
										String email = scanner.nextLine();
										System.out.print("변경할 전화번호를 입력하세요: _");
										String phoneNumber = scanner.nextLine();
										
										System.out.println("변경이 완료되었습니다.");
										System.out.println("====================================\n");
									}else if (menu.equals("4")) {
										/*
										 * delivered가 된 상품이 존재하면 리뷰달기
										 */
										System.out.println("\n========  주문 내역 조회  =============");
										
										System.out.println("======================================\n");
									}else if (menu.equals("5")) {
										System.out.println("\n=========  회원 탈퇴  ============");
										
										System.out.println("===================================\n");
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
							System.out.println("\n======= 전체 상품 보기 ==========");
							
							System.out.println("================================\n");
						}else if (menu.equals("2")) {
							//카테고리별 보기
							System.out.println("\n======= 카테고리별 보기 ========");
							System.out.print("카테고리를 입력해 주세요");
							String category = scanner.nextLine();
							
							System.out.println("================================\n");
						}else if (menu.equals("3")) {
							//가격대별 보기
							System.out.println("\n======== 가격대별 보기 ===========");
							System.out.print("번호를 선택해 주세요.(1. 3만원 미만, 2. 3-10만원, 3. 10-50만원,4. 50만원 이상)");
							int number = scanner.nextInt();
							scanner.nextLine();
							
							System.out.println("=================================\n");
						}else if (menu.equals("4")) {
							//베스트셀러
							System.out.println("\n========  베스트셀러  ==========");
							userService.findBestSeller();
							System.out.println("===============================\n");
						}else if (menu.equals("5")) {
							// 신상품은 등록 3일 이내 제품
							System.out.println("\n======== 신상품 보기 ============");
							
							System.out.println("================================\n");
						}else if (menu.equals("6")) {
							//상품 상세보기
							System.out.println("\n==========  상품 상세보기  ===========");
							System.out.print("상품 이름을 입력해 주세요");
							String itemname = scanner.nextLine();
							userService.showItemDetails(itemname);
							
							System.out.println("=====================================\n");
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
	public void itemController(Scanner scanner) throws ShoppingMallException {
		while(true) {
			System.out.println("┌────────────────────────────────────┐");
			System.out.println("│      📦 [관리자] 상품 관리              │");
			System.out.println("├────────────────────────────────────┤");
			System.out.println("│  1. 상품 등록                         │");
			System.out.println("│  2. 상품 수정                         │");
			System.out.println("│  3. 상품 삭제                         │");
			System.out.println("│  4. 재고 관리                         │");
			System.out.println("│  5. 상품 조회                         │");
			System.out.println("│  0. 돌아가기                          │");
			System.out.println("└────────────────────────────────────┘");
			System.out.print("메뉴를 선택하세요: _");
			
			String menu = scanner.nextLine();
			switch (menu) {
			case "0":
				return;
			case "1":
				System.out.println("\n===========   상품 등록   ============");
				System.out.print("상품 명을 입력해 주세요: _");
				String name = scanner.nextLine();
				System.out.print("상품 카테고리를 입력해 주세요: _");
				String category = scanner.nextLine();
				System.out.print("상품 가격을 입력해 주세요: _");
				String sPrice = scanner.nextLine().trim().replaceAll("원., ", "");
				int price = Integer.parseInt(sPrice);
				System.out.print("상품 수량을 입력해 주세요: _");
				String sQuantity = scanner.nextLine().trim().replaceAll("개., ", "");
				int quantity = Integer.parseInt(sQuantity);
				System.out.print("상품 설명을 입력해 주세요: _");
				String description = scanner.nextLine();
				Item newItem = new Item(name, category, price, quantity, description);
				productRepository.save(newItem);
				System.out.println("상품이 등록되었습니다!");
				System.out.println("=====================================");
				break;
			case "2":
				/*
				 *  가격, 설명
				 *  제품 명 받고 제품 명을 통한 item받아서 set으로 수정
				 */
				System.out.println("\n==========  상품 수정  ===========");
				System.out.print("변경 할 상품의 이름을 입력 해 주세요. \n변경하지 않을 경우 n을 입력 해 주세요 :_");
				String modifyItemName = scanner.nextLine().trim();
				ValidationUtils.requireNotNullAndEmpty(modifyItemName, "빈 문자를 입력하지 말아 주세요");
				if (modifyItemName.toLowerCase() == "n") {
					break;
				}
				Item modifyItem = userService.getItembyName(modifyItemName);
				
				ValidationUtils.requireNotNullItem(modifyItem, "변경 할 제품이 없습니다.");
				System.out.print("상품 가격, 상품 개수, 상품 설명을 변경 할 수 있습니다.\n 어떤 것을 변경하시겠습니까?\n가격, 개수, 설명을 입력 해 주세요(예. 개수, 가격)\n 변경 사항이 없을 시 n을 입력해주세요 :_");
				String modifyingItemList = scanner.nextLine();
				ValidationUtils.requireNotNullAndEmpty(modifyingItemList, "빈 문자를 입력하지 말아 주세요");
				if (modifyingItemList.toLowerCase() == "n") {
					break;
				}
				if (modifyingItemList.contains("가격")) {
					System.out.print("상품의 가격을 입력해주세요:_");
					sPrice = scanner.nextLine().trim().replaceAll("원., ", "");
					price = Integer.parseInt(sPrice);
					modifyItem.setPrice(price);
				}
				if (modifyingItemList.contains("개수")) {
					System.out.print("상품의 개수를 입력해주세요:_");
					sQuantity = scanner.nextLine().trim().replaceAll("개., ", "");
					ValidationUtils.requireMaxLength(sQuantity, 7, "상품 개수는 999,999개를 넘을 수 없습니다.");
					quantity = Integer.parseInt(sQuantity);
					modifyItem.setQuantity(quantity);
				}
				if (modifyingItemList.contains("설명")) {
					System.out.print("상품 설명을 입력해 주세요:_");
					description = scanner.nextLine();
					ValidationUtils.requireMaxLength(description, 500, "상품 설명은 500자를 넘을 수 없습니다.");
					modifyItem.setDescription(description);
				}
				FileManagement.writeToFile(Constants.PRODUCT_DATA_FILE, userService.getItems().values().stream().toList());
				System.out.println("상품이 수정되었습니다!");
				System.out.println("================================\n");
				break;
			case "3":
				System.out.println("\n=========   상품 삭제   ==========");
				System.out.print("삭제할 상품의 이름을 검색하세요, 삭제 하지 않을 경우 n을 입력 해 주세요: _");
				String deleteItemName = scanner.nextLine().trim();
				ValidationUtils.requireNotNullAndEmpty(deleteItemName, "빈 문자를 입력하지 말아 주세요");
				if (deleteItemName.toLowerCase() == "n") {
					break;
				}
				Item deleteItem = userService.getItembyName(deleteItemName);
				ValidationUtils.requireNotNullItem(deleteItem, "삭제 할 제품이 없습니다.");
				FileManagement.writeToFile(Constants.PRODUCT_DATA_FILE, userService.deleteItem(deleteItem).values().stream().toList());
				System.out.println("성공적으로 삭제되었습니다.");
				System.out.println("==================================\n");
				break;
			case "4":
				//재고 관리
				System.out.println("\n=========   재고 관리   ============");
				System.out.print("상품 재고 변경 할 상품의 이름을 입력 해 주세요. \n변경하지 않을 경우 n을 입력 해 주세요");
				modifyItemName = scanner.nextLine().trim();
				ValidationUtils.requireNotNullAndEmpty(modifyItemName, "빈 문자를 입력하지 말아 주세요");
				if (modifyItemName.toLowerCase() == "n") {
					break;
				}
				modifyItem = userService.getItembyName(modifyItemName);
				System.out.print("상품 개수를 증가시키겠습니까? 감소시키겠습니까?(추가, 감소), n을 입력하면 취소 됩니다 :_");
				String pmselect = scanner.nextLine().trim();
				ValidationUtils.requireNotNullAndEmpty(pmselect, "빈 문자를 입력하지 말아 주세요");
				if (pmselect.toLowerCase() == "n") {
					break;
				}
				if (pmselect.equals("추가") || pmselect.equals("증가") ) {
					System.out.print("개수를 입력해주세요: _");
					sQuantity = scanner.nextLine().trim().replaceAll("개., ", "");
					ValidationUtils.requireMaxLength(sQuantity, 7, "상품 개수는 999,999개를 넘을 수 없습니다.");
					quantity = Integer.parseInt(sQuantity);
					modifyItem.productPlusStock(quantity);
				}
				if (pmselect.equals("감소")) {
					System.out.print("개수를 입력해주세요: _");
					sQuantity = scanner.nextLine().trim().replaceAll("개., ", "");
					ValidationUtils.requireMaxLength(sQuantity, 7, "상품 개수는 999,999개를 넘을 수 없습니다.");
					quantity = Integer.parseInt(sQuantity);
					modifyItem.productMinusStock(quantity);
				}
				FileManagement.writeToFile(Constants.PRODUCT_DATA_FILE, userService.getItems().values().stream().toList());
				System.out.println("===================================\n");
				break;
			case "5":
				// 상품 둘러보기 메소드로 바로 가기
				break;
			default:
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				break;
			}
		}
	}
}