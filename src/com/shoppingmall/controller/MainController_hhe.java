package com.shoppingmall.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.shoppingmall.exception.ValidationException;
import com.shoppingmall.models.CartItem;
import com.shoppingmall.models.Customer;
import com.shoppingmall.models.Item;
import com.shoppingmall.models.Manager;
import com.shoppingmall.models.Person;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.repository.PersonRepository;
import com.shoppingmall.repository.ProductRepository;
import com.shoppingmall.service.ManagerService;
import com.shoppingmall.service.UserService;
import com.shoppingmall.util.Constants;
import com.shoppingmall.util.ValidationUtils;

public class MainController_hhe {
	private Scanner scanner;
	private ManagerService managerService;
	private UserService userService;
	private ProductRepository productRepository;
	private PersonRepository personRepository;
	
	public MainController_hhe() {
		this.scanner = new Scanner(System.in);
		managerService = new ManagerService("Java Shopping Mall");
		userService = new UserService("Java Shopping Mall");
		productRepository = new ProductRepository();
		personRepository = new PersonRepository();
	}

	public void start() {
		showMainMenu();
	}
	
	//메인메뉴
	private void showMainMenu() {
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
				
				case "1": //회원가입
						try {
							System.out.println("╔════════════════════════════════════════════╗");
							System.out.println("║     🛍️  "+userService.getMallName()+"회원가입          ║");
							System.out.println("╚════════════════════════════════════════════╝");
							System.out.println("\n[ 회원 정보를 입력해 주세요 ]\n────────────────────────────\n");
							System.out.print("▶ ID (5-20자, 영문/숫자 조합, 중복 불가) : ");
							String memberId = scanner.nextLine().trim();
	
							System.out.print("▶ 비밀번호 (8-20자, 영문/숫자 포함)	      : ");
							String memberPassword = scanner.nextLine().trim();
	
							System.out.print("▶ 이름 (2-20자, 한글/영문)     	      : ");
							String memberName = scanner.nextLine().trim();
	
							System.out.print("▶ 이메일 (이메일 형식, 중복 불가)		  : ");
							String memberEmail = scanner.nextLine().trim();
	
							System.out.print("▶ 전화번호(010-XXXX-XXXX 형식)		  : ");
							String memberPhone = scanner.nextLine().trim();
	
							System.out.print("▶ 주소 (50자 이내)   				  : ");
							String memberAddress = scanner.nextLine().trim();
	
							System.out.print("▶ 사용자:1 / 관리자2				      : ");
							String memberType = scanner.nextLine().trim();
	
					        // 1. ID 검증
					        ValidationUtils.requireNotNullAndEmpty(memberId, "아이디를 입력해 주세요.");
					        ValidationUtils.requireMinLength(memberId, 5, "❌ 아이디는 최소 5자 이상이어야 합니다.");
					        ValidationUtils.requireMaxLength(memberId, 20, "❌ 아이디는 최대 20자 이하여야 합니다.");
					        if (!memberId.matches("^[a-zA-Z0-9]+$")) {
					            throw new ValidationException("❌ 아이디는 영문과 숫자만 사용 가능합니다.");
					        }
					        
					        // 2. 비밀번호 검증
					        ValidationUtils.requireNotNullAndEmpty(memberPassword, "비밀번호를 입력해 주세요.");
					        ValidationUtils.requireMinLength(memberPassword, 8, "비밀번호는 최소 8자 이상이어야 합니다.");
					        ValidationUtils.requireMaxLength(memberPassword, 20, "비밀번호는 최대 20자 이하여야 합니다.");
					        if (!memberPassword.matches(".*[a-zA-Z].*") || !memberPassword.matches(".*[0-9].*")) {
					            throw new ValidationException("비밀번호는 영문과 숫자를 모두 포함해야 합니다.");
					        }
					        
					        // 3. 이름 검증
					        ValidationUtils.requireNotNullAndEmpty(memberName, "이름을 입력해 주세요.");
					        ValidationUtils.requireMinLength(memberName, 2, "이름은 최소 2자 이상이어야 합니다.");
					        ValidationUtils.requireMaxLength(memberName, 20, "이름은 최대 20자 이하여야 합니다.");
					        if (!memberName.matches("^[가-힣a-zA-Z]+$")) {
					            throw new ValidationException("이름은 한글 또는 영문만 사용 가능합니다.");
					        }
					        
					        // 4. 이메일 검증
					        ValidationUtils.requireNotNullAndEmpty(memberEmail, "이메일을 입력해 주세요.");
					        if (!(memberEmail.contains("@") && memberEmail.contains("."))) {
					            System.out.println("이메일 형식이 잘못되었습니다.");
					        }
					        
					        // 5. 전화번호 검증
					        ValidationUtils.requireNotNullAndEmpty(memberPhone, "전화번호를 입력해 주세요.");
					        if (!(memberPhone.startsWith("010-") && memberPhone.length() == 13)) {
					            System.out.println("전화번호는 010-0000-0000 형식이어야 합니다.");
					        }
					        
					        // 6. 주소 검증
					        ValidationUtils.requireNotNullAndEmpty(memberAddress, "주소를 입력해 주세요.");
					        ValidationUtils.requireMaxLength(memberAddress, 50, "주소는 50자 이내로 입력해 주세요.");
					        
					        // 7. 사용자 타입 검증
					        ValidationUtils.requireNotNullAndEmpty(memberType, "사용자 타입을 입력해 주세요.");
					        if (memberType != "1" && memberType !="2") {
					            throw new ValidationException("❗ 사용자 타입은 1(사용자) 또는 2(관리자)를 입력해 주세요.");
					        }
	
					        // 중복 검사 1. 아이디
					        if (PersonRepository.existsById(memberId)) {
					            System.out.println("❌ 이미 사용 중인 아이디입니다.");
					            return;
					        }
					        
					        // 중복 검사 2. 이메일
					        if (PersonRepository.isExistingEmail(memberEmail)) {
					            System.out.println("❌ 이미 사용 중인 이메일입니다.");
					            return;
					        }
	
							// 회원 등록 
					        Person newMember = memberType.equals("1")? 
					        		new Customer(memberId, memberPassword, memberName, memberAddress, memberEmail, memberPhone)
					        	  : new Manager(memberId, memberPassword, memberName, memberAddress, memberEmail, memberPhone);
					        
					        // 저장까지 하기!
					        personRepository.savePerson(newMember); 
					        System.out.println("✅ 회원가입이 완료되었습니다!");
						
						} catch (ValidationException e) {
							System.out.println("오류가 발생했습니다 : " + e.getMessage());
						}
						
						break;
					
					
				case "2":
					LoginController loginController = LoginController.getInstance();
					String userRole = loginController.login();
				    
				    if(userRole != null) {
				        System.out.println("====================================\n");
				        
				        if(userRole.equals("관리자")) {
				            // 관리자 로그인 메뉴 여기에 넣어주세요
				        } else {
				            // 일반 사용자 메뉴 여기에 넣어주세요
				        }
				    }
				    //로그인 실패
				    break;
				    
				    
				    
				    
				    
				    
				    
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
										System.out.println("\n===========   상품 등록   ============");
										System.out.print("상품 명을 입력해 주세요: _");
										String name = scanner.nextLine();
										System.out.print("상품 카테고리를 입력해 주세요: _");
										String category = scanner.nextLine();
										System.out.print("상품 가격을 입력해 주세요: _");
										String sPrice = scanner.nextLine();
										int price = Integer.parseInt(sPrice);
										System.out.print("상품 수량을 입력해 주세요: _");
										String sQuantity = scanner.nextLine();
										int quantity = Integer.parseInt(sQuantity);
										System.out.print("상품 설명을 입력해 주세요: _");
										String description = scanner.nextLine();
										Item newItem = new Item(name, category, price, quantity, description);
										productRepository.save(newItem);
										
										System.out.println("상품이 등록되었습니다!");
										System.out.println("=====================================");
									}else if (menu.equals("2")) {
										/*
										 *  가격, 설명
										 *  제품 명 받고 제품 명을 통한 item받아서 set으로 수정
										 */
										System.out.println("\n==========  상품 수정  ===========");
										System.out.println("상품의 이름을 입력해주세요: _");
										String name = scanner.nextLine();
										System.out.print("상품의 가격을 입력해주세요: _");
										String sPrice = scanner.nextLine();
										int price = Integer.parseInt(sPrice);
										System.out.print("상품의 설명을 입력해주세요: _");
										String description = scanner.nextLine();
										
										System.out.println("상품이 수정되었습니다!");
										System.out.println("================================\n");
									}else if (menu.equals("3")) {
										System.out.println("\n=========   상품 삭제   ==========");
										System.out.print("삭제할 상품의 이름을 검색하세요: _");
										String name = scanner.nextLine();
										
										System.out.println("성공적으로 삭제되었습니다.");
										System.out.println("==================================\n");
									}else if (menu.equals("4")) {
										//재고 관리
										System.out.println("\n=========   재고 관리   ============");
										
										System.out.println("===================================\n");
									}else if (menu.equals("5")) {
										System.out.println("\n=========   상품 목록 조회   ==========");
								
										System.out.println("======================================\n");
									}else if (menu.equals("0")) {
										break;
									}else {
										System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
									}
								}
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
									
									switch(menu) {
									
									case "1":
										System.out.println("\n=======  전체 회원 조회  =========");
										
										List<Customer> customerList = FileManagement.readFromFile(Constants.USER_DATA_FILE);
								        for (Customer c : customerList) {
								            System.out.println(customer.toString());
								        }
										
										System.out.println("=================================\n");
										
										break;
										
									case "2":
										System.out.println("\n========   회원 검색   =========");
										System.out.print("🔍 검색할 회원의 이름을 입력하세요: _");
										String searchName = scanner.nextLine();
										
										List<Person> foundMembers = PersonRepository.findByNameContains(searchName);
										if (foundMembers.isEmpty()) {
										    System.out.println("❌ 검색 결과가 없습니다.");
										} else {
										    System.out.println("검색 결과:");
										    for (Person person : foundMembers) {
										        System.out.println(person.toString());
										    }
										}
										System.out.println("==============================\n");
										
										break;

										
									case "3":
										System.out.println("\n========  회원 상세 정보  =========");
										System.out.print("🔍 정보를 확인할 회원의 id를 입력하세요: _");
										String searchId = scanner.nextLine();
										
										PersonRepository.showMemberDetails(searchId);
										
										System.out.println("==================================");
										
										break;

										
									case "4":
										System.out.println("\n========   회원 강제 탈퇴   ========");
										
										System.out.print("🔍 회원 탈퇴를 원하는 ID를 입력해주세요: _");
										String leaveId = scanner.nextLine();
										
										System.out.print("⚠️ 해당 회원을 탈퇴하시겠습니까?(y/n) " + leaveId + " : ");
										String yesOrNo = scanner.nextLine().trim().toLowerCase();
										
										switch(yesOrNo) {
											case "y" :
												PersonRepository repo = new PersonRepository(); 
												repo.deleteById(leaveId);

												System.out.println("====================================");
												System.out.println("☑️ 해당 ID는 탈퇴되었습니다 : "+leaveId);
												System.out.println("====================================");
												break;
											case "n" :
												System.out.println("회원 탈퇴 진행을 취소합니다");
												break;
											default :
												System.out.println("❌ 다음을 입력해주세요: y 또는 n");
												break;
											}
											break;
											
									case "0":
										System.out.println("이전 화면으로 돌아갑니다.");
										break;
										
									default:
										System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
										break;
									
					}}}else {
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
										ArrayList<CartItem> cartItems = userService.getCarts().get(customerId);
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
					}}
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
}


		
