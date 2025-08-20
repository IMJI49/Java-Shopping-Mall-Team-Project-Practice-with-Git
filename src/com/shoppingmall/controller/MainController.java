package com.shoppingmall.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.shoppingmall.exception.ShoppingMallException;
import com.shoppingmall.exception.ValidationException;
import com.shoppingmall.models.CartItem;
import com.shoppingmall.models.Customer;
import com.shoppingmall.models.Item;
import com.shoppingmall.models.Manager;
import com.shoppingmall.models.Order;
import com.shoppingmall.models.Person;
import com.shoppingmall.persistence.FileManagement;
import com.shoppingmall.repository.PersonRepository;
import com.shoppingmall.repository.ProductRepository;
import com.shoppingmall.service.ManagerService;
import com.shoppingmall.service.UserService;
import com.shoppingmall.util.Constants;
import com.shoppingmall.util.ValidationUtils;
import com.shoppingmall.util.SessionManager;

public class MainController {
	private Scanner scanner;
	private ManagerService managerService;
	private UserService userService;
	private ProductRepository productRepository;
	private PersonRepository personRepository;
	
	public MainController() {
		this.scanner = new Scanner(System.in);
		managerService = new ManagerService("Java Shopping Mall");
		userService = new UserService("Java Shopping Mall");
		productRepository = new ProductRepository();
		personRepository = new PersonRepository();
	}

	public void start() throws ShoppingMallException {
		showMainMenu();
	}
	
	//메인메뉴
	private void showMainMenu() throws ShoppingMallException {
		while(true) {
			// 메인메뉴
			Customer customer = null;
			Manager manager;
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
					try {
						System.out.println("╔════════════════════════════════════════════╗");
						System.out.println("║     🛍️  "+userService.getMallName()+"회원가입          ║");
						System.out.println("╚════════════════════════════════════════════╝");
						System.out.println("\n[ 회원 정보를 입력해 주세요 ]\n────────────────────────────\n");
						System.out.print("▶ ID (5-20자, 영문/숫자 조합, 중복 불가) : ");
						String memberId = scanner.nextLine().trim();

						System.out.print("▶ 비밀번호 (8-20자, 영문/숫자 포함) : ");
						String memberPassword = scanner.nextLine().trim();

						System.out.print("▶ 이름 (2-20자, 한글/영문): ");
						String memberName = scanner.nextLine().trim();

						System.out.print("▶ 이메일 (이메일 형식, 중복 불가): ");
						String memberEmail = scanner.nextLine().trim();

						System.out.print("▶ 전화번호(010-XXXX-XXXX 형식): ");
						String memberPhone = scanner.nextLine().trim();

						System.out.print("▶ 주소 (50자 이내) : ");
						String memberAddress = scanner.nextLine().trim();

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
				        Customer newMember = new Customer(memberId, memberPassword, memberName, memberAddress, memberEmail,memberPhone );
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
				    Person currentUser;
				    //로그인 실패
				    SessionManager.setCurrentManager(currentUser);
				    break;
					if(SessionManager.getCurrentUser().getRole().equals("관리자")) {
						manager = (Manager) currentUser;
						// 관리자 로그인 메뉴
						String menu2;
						do {
							System.out.println("╔════════════════════════════════════════════╗");
							System.out.println("║     🛍️  "+userService.getMallName()+"                 ║");
							System.out.println("║      [관리자 모드] 환영합니다!                   ║");
							System.out.println("╚════════════════════════════════════════════╝");
							System.out.println("1. 주문 관리");
							System.out.println("2. 마이페이지");
							System.out.println("3. [관리] 상품 관리");
							System.out.println("4. [관리] 사용자 관리");
							System.out.println("0. 로그아웃");
							System.out.print("메뉴를 선택하세요: _");
							
							menu2 = scanner.nextLine();
							switch(menu2) {
								case "1":
									adminOrderManageMenu();
									break;
								case "2":
									// 관리자 마이페이지
									String menu3;
									do {
										System.out.println("┌────────────────────────────────────┐");
										System.out.println("│    👤[관리자 모드] 마이페이지            │");
										System.out.println("├────────────────────────────────────┤");
										System.out.println("│  1. 내 정보 조회                      │");
										System.out.println("│  2. 비밀번호 변경                      │");
										System.out.println("│  3. 개인정보 수정                     │");
										System.out.println("│  0. 돌아가기                         │");
										System.out.println("└────────────────────────────────────┘");
										System.out.print("메뉴를 선택하세요: _");
										
										menu3 = scanner.nextLine();			
										switch(menu3) {
											case "1":
												//내 정보 조회
												System.out.println("\n========  내 정보 조회  ==========");
												System.out.println(manager);
												System.out.println("===============================\n");
												break;
											case "2":
												System.out.println("\n======== 비밀번호 변경 ==========");
												System.out.print("변경할 비밀번호를 입력해주세요: _");
												String changePassword = scanner.nextLine();
												ValidationUtils.requireNotNullAndEmpty(changePassword, "비밀번호를 입력해 주세요.");
										        ValidationUtils.requireMinLength(changePassword, 8, "비밀번호는 최소 8자 이상이어야 합니다.");
										        ValidationUtils.requireMaxLength(changePassword, 20, "비밀번호는 최대 20자 이하여야 합니다.");
										        if (!changePassword.matches(".*[a-zA-Z].*") || !changePassword.matches(".*[0-9].*")) {
										            throw new ValidationException("비밀번호는 영문과 숫자를 모두 포함해야 합니다.");
										        }
										        userService.changePassword(customer, changePassword);
												System.out.println("변경이 완료되었습니다.");
												System.out.println("====================================\n");
												break;
											case "3":
												System.out.println("\n======== 개인정보 수정 ==========");
												System.out.print("변경할 주소를 입력하세요: _");
												String address = scanner.nextLine();
												userService.updateAddress(customer.getId(), menu, address);
												System.out.print("변경할 이메일을 입력하세요: _");
												String email = scanner.nextLine();
												System.out.print("변경할 전화번호를 입력하세요: _");
												String phoneNumber = scanner.nextLine();
												
												System.out.println("변경이 완료되었습니다.");
												System.out.println("====================================\n");
												break;
											case "0":
												break;
											default:
												System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
												break;
										}
									}while(!menu3.equals("0"));
									break;
								case "3":
									itemController(scanner);
								case "4":
									// 관리자 사용자 관리 메뉴
									do {
										System.out.println("┌────────────────────────────────────┐");
										System.out.println("│      👥 [관리자 모드] 사용자 관리        │");
										System.out.println("├────────────────────────────────────┤");
										System.out.println("│  1. 전체 회원 조회                     │");
										System.out.println("│  2. 회원 검색(이름으로 검색)            │");
										System.out.println("│  3. 회원 상세 정보(id로 검색)           │");
										System.out.println("│  4. 회원 강제 탈퇴                    │");
										System.out.println("│  0. 돌아가기                         │");
										System.out.println("└────────────────────────────────────┘");
										System.out.print("메뉴를 선택하세요: _");
										
										menu3 = scanner.nextLine();
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
												System.out.print("정보를 확인할 회원의 id를 입력하세요: _");
												String searchId = scanner.nextLine();
												PersonRepository.showMemberDetails(searchId);
												System.out.println("==================================");
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
												System.out.println("====================================");
											case "0":
												break;
											default:
												System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
												break;
										}
									}while(!menu3.equals("0"));
									break;
								case "0":
									break;
								default:
									System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
									break;
							
							}
						}while(!menu2.equals("0"));
					}else {
						// 일반 사용자 메뉴
						customer = (Customer) currentUser;
						String menu2;
						do {
							System.out.println("╔════════════════════════════════════════════╗");
							System.out.println("║     🛍️  Java Shopping Mall                 ║");
							System.out.printf("║       환영합니다, [%s]님!                 ║\n", SessionManager.getCurrentUser().getId());
							System.out.println("╚════════════════════════════════════════════╝");
							System.out.println("1. 상품 둘러보기");
							System.out.println("2. 상품 검색");
							System.out.println("3. 장바구니 관리");
							System.out.println("4. 주문하기");
							System.out.println("5. 주문내역");
							System.out.println("6. 마이페이지");
							System.out.println("0. 로그아웃");
							System.out.print("\n메뉴를 선택하세요: _");
							
							menu2 = scanner.nextLine();
							switch(menu2) {
								case "1":
									// 상품 둘러보기 메뉴
									lookAroundGoods();
									break;
								case "2":
									//상품 검색
									System.out.print("검색할 상품의 상품명을 입력해주세요: _");
									String name = scanner.nextLine();
									break;
								case "3":
									// 장바구니 관리
									cartManageMenu(customer);
								case "4":
									//주문하기
									System.out.println("\n===============  주문하기 ================");
									placeOrderMenu(customer);
									System.out.println("==========================================\n");
									break;
								case "5":
									System.out.println("\n=============  주문내역  ===============");
									showUserOrdersMenu(customer);
									System.out.println("========================================\n");
									break;
								case "6":
								// 일반 사용자 마이페이지
									String menu3;
									do {
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
										
										menu3 = scanner.nextLine();
										switch(menu3) {
											case "1":
												System.out.println("\n======== 내 정보 조회 ========");
												System.out.print("비밀번호를 입력해주세요. : ");
												String nowPassword = scanner.nextLine();
												System.out.println(SessionManager.getCurrentUser().toString());
												System.out.println("==============================\n");
												break;
												
											case "2":
												System.out.println("\n======== 비밀번호 변경  ========");
												System.out.print("변경할 비밀번호를 입력해주세요: _");
												String changePassword = scanner.nextLine();
												
												System.out.println("비밀번호가 변경되었습니다!");
												System.out.println("================================\n");
												break;
												
											case "3":
												System.out.println("\n======== 개인정보를 변경합니다 ==========");
												System.out.print("변경할 주소를 입력하세요: _");
												String address = scanner.nextLine();
												System.out.print("변경할 이메일을 입력하세요: _");
												String email = scanner.nextLine();
												System.out.print("변경할 전화번호를 입력하세요: _");
												String phoneNumber = scanner.nextLine();
												
												userService.updateAddress(customer.getId(), nowPassword, address);
												userService.updateEmail(customer.getId(), nowPassword, email);
												userService.updatePhone(customer.getId(), nowPassword, phoneNumber);
												System.out.println("변경이 완료되었습니다.");
												System.out.println("====================================\n");
												break;
												
											case "4":
												/*
												 * delivered가 된 상품이 존재하면 리뷰달기
												 */
												System.out.println("\n========  주문 내역 조회  =============");
												
												System.out.println("======================================\n");
												break;
												
											case "5":
												System.out.println("\n=========  회원 탈퇴  ============");
												
												System.out.println("===================================\n");
												break;
												
											case "0":
												break;
											default:
												System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
												break;
										}
									}while(!menu3.equals("0"));
									break;
								case "0":
									break;
								default:
									break;
							}
						}while(!menu2.equals("0"));
					}
					break;
				case "3":
					//상품 둘러보기 메뉴
					lookAroundGoods();
					break;
				case "0":
					return;
				default:
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					break;
			}
		}
	}

	private void lookAroundGoods() throws ValidationException {
		String menu;
		do {
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
			switch(menu) {
				case "1":
					System.out.println("\n======= 전체 상품 보기 ==========");
					userService.getAllItems();
					System.out.println("================================\n");
					break;
				case "2":
					//카테고리별 보기
					System.out.println("\n======= 카테고리별 보기 ========");
					System.out.print("카테고리를 입력해 주세요");
					String category = scanner.nextLine();
					userService.findByCategory(category);
					System.out.println("================================\n");
					break;
				case "3":
					//가격대별 보기
					System.out.println("\n======== 가격대별 보기 ===========");
					System.out.print("번호를 선택해 주세요.(1. 3만원 미만, 2. 3-10만원, 3. 10-50만원,4. 50만원 이상)");
					int number = scanner.nextInt();
					scanner.nextLine();
					userService.findByPriceRange(number);
					System.out.println("=================================\n");
					break;
				case "4":
					//베스트셀러
					System.out.println("\n========  베스트셀러  ==========");
					userService.findBestSeller();
					System.out.println("===============================\n");
					break;
				case "5":
					// 신상품은 등록 3일 이내 제품
					System.out.println("\n======== 신상품 보기 ============");
					userService.findNewItem();
					System.out.println("================================\n");
					break;
				case "6":
					//상품 상세보기
					System.out.println("\n==========  상품 상세보기  ===========");
					System.out.print("상품 이름을 입력해 주세요");
					String itemname = scanner.nextLine();
					userService.showItemDetails(itemname);
					System.out.println("=====================================\n");
					break;
				case "0":
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					break;
			}
		}while(!menu.equals("0"));
	}

	
	 // 1. 관리자 모드 주문관리
    public void adminOrderManageMenu() {
        while (true) {
            System.out.println("┌────────────────────────────────────┐");
            System.out.println("│   📦[관리자 모드] 주문 관리             │");
            System.out.println("├────────────────────────────────────┤");
            System.out.println("│  1. 주문 내역 확인                    │");
            System.out.println("│  2. 주문 confirm                    │");
            System.out.println("│  3. 주문 취소                        │");
            System.out.println("│  0. 돌아가기                         │");
            System.out.println("└────────────────────────────────────┘");
            System.out.print("메뉴를 선택하세요: _");
            String menu = scanner.nextLine();
            switch(menu) {
                case "1":
                    managerService.showAllOrders();
                    break;
                case "2":
                    System.out.print("확정할 주문번호를 입력하세요: ");
                    String confirmOrderId = scanner.nextLine().trim();
                    try {
                        Order order = (Order)managerService.getOrders().get(confirmOrderId);
                        managerService.confirmOrder(order != null ? order.getStatus() : null, confirmOrderId);
                        System.out.println("주문이 확정되었습니다.");
                    } catch(Exception e) {
                        System.err.println("확정 실패: " + e.getMessage());
                    }
                    break;
                case "3":
                    System.out.print("취소할 주문번호를 입력하세요: ");
                    String cancelOrderId = scanner.nextLine().trim();
                    try {
                        Order order = (Order)managerService.getOrders().get(cancelOrderId);
                        managerService.cancelOrder(order != null ? order.getStatus() : null, cancelOrderId);
                        System.out.println("주문이 취소되었습니다.");
                    } catch(Exception e) {
                        System.err.println("취소 실패: " + e.getMessage());
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
            System.out.println();
        }
    }

    // 2. 사용자 장바구니 관리
    public void cartManageMenu(Customer customer) {
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
            String menu = scanner.nextLine();
            String userId = customer.getId();
            ArrayList<CartItem> cartItems = userService.getCarts().get(userId);
            switch(menu) {
                case "1":
                    if(cartItems == null || cartItems.isEmpty()) {
                        System.out.println("장바구니가 비어 있습니다.");
                    } else {
                        System.out.println("[장바구니 현재 목록]");
                        for(CartItem ci : cartItems) {
                            System.out.println(ci);
                        }
                    }
                    break;
                case "2":
                    System.out.print("추가할 상품의 이름을 입력해주세요: _");
                    String pName = scanner.nextLine();
                    Item item = userService.getItembyName(pName);
                    if(item == null) {
                        System.out.println("존재하지 않는 상품입니다.");
                        break;
                    }
                    System.out.print("추가 수량을 입력하세요: ");
                    try {
                        int qty = Integer.parseInt(scanner.nextLine().trim().replaceAll("개., ", ""));
                        userService.addCart(customer, pName, qty);
                        System.out.println("장바구니에 상품이 추가되었습니다.");
                    } catch(Exception e) {
                        System.out.println("추가 실패: " + e.getMessage());
                    }
                    break;
                case "3":
                    System.out.print("수량을 변경할 상품 이름: ");
                    String targetName = scanner.nextLine();
                    boolean found = false;
                    if(cartItems != null) {
                        for(CartItem ci : cartItems) {
                            if(ci.getItem().getName().equals(targetName)) {
                                System.out.print("새 수량을 입력하세요: ");
                                try {
                                    int newQty = Integer.parseInt(scanner.nextLine().trim().replaceAll("개., ", ""));
                                    if(newQty <= 0) {
                                        System.out.println("수량은 1 이상이어야 합니다.");
                                        break;
                                    }
                                    cartItems.remove(ci);
                                    userService.addCart(customer, targetName, newQty);
                                    System.out.println("수량이 변경되었습니다.");
                                    found = true;
                                    break;
                                } catch(Exception e) {
                                    System.out.println("변경 실패: " + e.getMessage());
                                }
                            }
                        }
                    }
                    if(!found) System.out.println("장바구니에 해당 상품이 없습니다.");
                    break;
                case "4":
                    System.out.print("삭제할 상품의 상품명을 입력해주세요: _");
                    String delName = scanner.nextLine();
                    boolean removed = false;
                    if(cartItems != null)
                        removed = cartItems.removeIf(ci -> ci.getItem().getName().equals(delName));
                    if(removed) {
                        System.out.println("장바구니에서 상품이 삭제되었습니다.");
                    } else {
                        System.out.println("장바구니에 해당 상품이 없습니다.");
                    }
                    break;
                case "5":
                    if(cartItems != null) cartItems.clear();
                    System.out.println("장바구니가 비워졌습니다.");
                    break;
                case "0":
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
            System.out.println();
        }
    }

    // 3. 사용자 주문하기
    public void placeOrderMenu(Customer customer) {
        try {
        	System.out.print("배송지를 변경하시겠습니까? y/n:_");
        	String changeAddressboolean = scanner.nextLine();
        	if (changeAddressboolean.toLowerCase() == "n") {
        		userService.placeOrder(customer);
        		System.out.println("주문이 정상적으로 완료되었습니다.");
				return;
			}
        	System.out.println("변경할 주소지를 입력해 주세요");
        	String changeAddress = scanner.nextLine();
            userService.placeOrder(customer, changeAddress);
            System.out.println("주문이 정상적으로 완료되었습니다.");
        } catch(Exception e) {
            System.err.println("주문 실패: " + e.getMessage());
        }
        System.out.println();
    }

    // 4. 사용자 주문내역 조회
    public void showUserOrdersMenu(Customer customer) {
        HashMap<String, Order> orders = userService.getOrders();
        System.out.println("=== 주문 내역 ===");
        if (customer.getOrderIDs().size() == 0) {
        	System.out.println("주문내역이 없습니다.");
        	return;
		}
        for (String or : customer.getOrderIDs()) {
			System.out.println(orders.get(or));
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
				lookAroundGoods();
				break;
			default:
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				break;
			}
		}
	}
}