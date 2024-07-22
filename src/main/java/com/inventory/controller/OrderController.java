package com.inventory.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inventory.repositories.vo.BookInventoryVo;
import com.inventory.repositories.vo.BookVo;
import com.inventory.repositories.vo.OrderVo;
import com.inventory.repositories.vo.UserVo;
import com.inventory.services.BookInventoryService;
import com.inventory.services.BookService;
import com.inventory.services.OrderService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/branch/order")
@Controller
public class OrderController {
	@Autowired
	BookService bookService;
	@Autowired
	OrderService orderService;
	@Autowired
	BookInventoryService bookInventoryService;

	// 현재 지점의 재고 데이터를 가져오는 메서드
	@GetMapping("/getData")
	@ResponseBody
	public List<BookInventoryVo> getData(HttpSession session) {
		UserVo vo = (UserVo) session.getAttribute("authUser");
		return bookInventoryService.getList(vo.getBranchId());
	}

	// 지점의 재고에서 특정 책을 검색하는 메서드
	@GetMapping("/searchBooks")
	@ResponseBody
	public List<BookInventoryVo> searchBooks(@RequestParam("query") String query, HttpSession session) {
		UserVo vo = (UserVo) session.getAttribute("authUser");
		return bookInventoryService.search(vo.getBranchId(), query);
	}

	// 세션에 수량을 저장하는 메서드
	@PostMapping("/saveQuantities")
	@ResponseBody
	public String saveQuantities(@RequestBody List<Map<String, Object>> bookQuantities, HttpSession session) {
		try {
			// 세션에서 기존 데이터를 가져오기
			List<Map<String, Object>> pastQuantities = (List<Map<String, Object>>) session.getAttribute("quantities");
			if (pastQuantities == null) {
				// 기존 값이 없는 경우 새 리스트를 생성
				pastQuantities = new ArrayList<>();
			}

			// 전달된 데이터로 기존 리스트 업데이트
			for (Map<String, Object> newQuantity : bookQuantities) {
				String bookCode = (String) newQuantity.get("bookCode");
				int newQuantityValue = (Integer) newQuantity.get("quantity");

				// 기존 항목 찾기
				boolean found = false;
				for (Map<String, Object> existingQuantity : pastQuantities) {
					if (existingQuantity.get("bookCode").equals(bookCode)) {
						// 기존 수량에 새로운 수량을 추가
						int existingQuantityValue = (Integer) existingQuantity.get("quantity");
						existingQuantity.put("quantity", existingQuantityValue + newQuantityValue);
						found = true;
						break;
					}
				}

				if (!found) {
					// 새로운 항목 추가
					pastQuantities.add(newQuantity);
				}
			}

			// 세션에 업데이트된 수량 저장
			session.setAttribute("quantities", pastQuantities);
			System.err.println(pastQuantities);
			return "Quantities saved to session successfully";
		} catch (Exception e) {
			e.printStackTrace(); // 로그에 오류 정보 기록
			return "Error saving quantities";
		}
	}

	// 세션에 저장된 장바구니 항목을 가져오는 메서드
	@GetMapping("/getCart")
	@ResponseBody
	public List<Map<String, Object>> getCart(HttpSession session) {
		List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("quantities");
		if (cart == null) {
			cart = new ArrayList<>();
		}
		return cart;
	}

	// 장바구니에서 항목을 삭제하는 메서드
	@PostMapping("/deleteFromCart")
	@ResponseBody
	public String deleteFromCart(@RequestParam("bookCode") String bookCode, HttpSession session) {
		// 세션에서 장바구니 데이터 가져오기
		List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("quantities");
		if (cart == null) {
			cart = new ArrayList<>();
		}

		// 장바구니에서 항목 제거
		Iterator<Map<String, Object>> iterator = cart.iterator();
		boolean itemRemoved = false;

		while (iterator.hasNext()) {
			Map<String, Object> item = iterator.next();
			if (bookCode.equals(item.get("bookCode"))) {
				iterator.remove(); // 항목 제거
				itemRemoved = true;
				break;
			}
		}

		// 세션에 업데이트된 장바구니 저장
		session.setAttribute("quantities", cart);

		if (itemRemoved) {
			return "Item removed from cart.";
		} else {
			return "Item not found in cart.";
		}
	}

	// 장바구니를 비우는 메서드
	@PostMapping("/clearCart")
	@ResponseBody
	public String clearCart(HttpSession session) {
	    // 장바구니 데이터를 가져옴
	    Object cartObj = session.getAttribute("quantities");

	    if (cartObj == null) {
	        return "Cart is already empty.";
	    }

	    // 장바구니가 비어 있는지 확인
	    List<?> cart = (List<?>) cartObj;
	    if (cart.isEmpty()) {
	        return "Cart is already empty.";
	    }

	    // 장바구니를 비움
	    session.removeAttribute("quantities");
	    return "Cart cleared successfully!";
	}
	
	// 주문 페이지를 로드하는 메서드
	@RequestMapping("/form")
	public String orderList(HttpSession session, Model model) {
		// 로그인 시 저장한 session authUser를 받아와 branchId 기반으로 지점 branch_inventory 테이블 데이터 연결
		// 데이터를 받아와 지점 교재 재고 현황을 모델에 저장
		UserVo vo = (UserVo) session.getAttribute("authUser");

		List<BookInventoryVo> list = bookInventoryService.getList(vo.getBranchId());
		model.addAttribute("list", list);

		Boolean addCart = (Boolean) session.getAttribute("addCart");
		if (addCart != null && addCart) {
			model.addAttribute("addCart", true);
			session.removeAttribute("addCart");
		}
		// session에 저장된 장바구니 리스트를 받아오고 모델에 추가해 jsp에 전달
		Object cartObject = session.getAttribute("cart");
		List<OrderVo> cartList = (List<OrderVo>) cartObject;
		if (cartList == null) {
			cartList = new ArrayList<>();
		}
		for (OrderVo orderVo : cartList) {
			BookInventoryVo book = new BookInventoryVo();
			book.setBookCode(orderVo.getBookCode());
			book.setBranchId(vo.getBranchId());
			orderVo.setInventory(bookInventoryService.getInventory(book));
		}

		Boolean success = (Boolean) session.getAttribute("success");
		if (success != null && !success) {
			model.addAttribute("success", false);
			session.removeAttribute("success");
		}

		model.addAttribute("cartList", cartList);
		return "branches/branch_order_form";
	}
	
	// 주문 기록 페이지를 로드하는 메서드
	@RequestMapping("/list")
	public String orderHistory(Model model, HttpSession session) {
		// 로그인 시 저장한 session authUser를 받아옴
		UserVo vo = (UserVo) session.getAttribute("authUser");

		// branchId 기반으로 주문 기록을 가져와 리스트에 저장 후 모델에 실어서 jsp에 전달
		List<OrderVo> list = orderService.getOrderList(vo.getBranchId());
		model.addAttribute("list", list);

		Boolean success = (Boolean) session.getAttribute("success");
		if (success != null && success) {
			model.addAttribute("success", true);
			session.removeAttribute("success");
		}

		return "branches/branch_order_list";
	}
	
	// 주문을 확정하는 메서드
	@RequestMapping("/submit")
	public String ordering(HttpSession session, Model model) {
		UserVo vo = (UserVo) session.getAttribute("authUser");

		List<LinkedHashMap> cartData = (List<LinkedHashMap>) session.getAttribute("quantities");

		// 장바구니가 있으면 book_order 테이블에 지점 아이디 기반으로 데이터 생성
		if (cartData != null && !cartData.isEmpty()) {
			orderService.insert(vo);

			for (LinkedHashMap map : cartData) {
				OrderVo orderVo = new OrderVo();
				orderVo.setBookCode((String) map.get("bookCode"));
				orderVo.setBookName((String) map.get("bookName"));
				orderVo.setQuantity((Integer) map.get("quantity"));
				orderVo.setOrderId(orderService.getMax());
				orderVo.setUserName(vo.getName());
				orderService.insertDetail(orderVo);
			}

			// 장바구니 리스트 삭제
			session.removeAttribute("quantities");

		} else {
			session.setAttribute("success", false);
			session.removeAttribute("quantities");
			return "redirect:/branch/order/form";
		}

		session.setAttribute("success", true);
		return "redirect:/branch/order/list";
	}
	
	// 주문 상세 페이지를 로드하는 메서드
	@RequestMapping("/detail")
	public String orderDetail(@RequestParam("orderId") String orderId, Model model) {
		// 받아온 orderId 기반으로 주문 상세 페이지로 연결
		List<OrderVo> list = orderService.getDetailList(orderId);
		for (OrderVo vo : list) {
			BookVo bookVo = bookService.getData(vo.getBookCode());
			vo.setBookName(bookVo.getBookName());
			vo.setPrice(bookVo.getPrice());
		}
		model.addAttribute("list", list);
		model.addAttribute("orderId", orderId);
		return "branches/branch_order_detail";
	}




//===================================================================== 

//	@PostMapping("/addMultiple")
//	public String addMultipleBooksToCart(@RequestParam("bookCodes") List<String> bookCodes,
//			@RequestParam("quantities") List<Integer> quantities, Model model, HttpSession session) {
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		List<OrderVo> cart = (List<OrderVo>) session.getAttribute("cart");
//
//		// 세션에 저장된 cart가 null인 경우, 새로운 ArrayList를 생성하여 초기화
//		if (cart == null) {
//			cart = new ArrayList<>();
//			session.setAttribute("cart", cart); // 세션에 초기화된 cart를 저장
//		}
//
//		System.out.println(bookCodes);
//		System.out.println(quantities);
//
//		// 받아온 bookCodes와 quantities를 순회하면서 장바구니(cart)에 추가
//		for (int i = 0; i < bookCodes.size(); i++) {
//			String bookCode = bookCodes.get(i);
//			int quantity = quantities.get(i);
//
//			// quantity가 0보다 작거나 같으면 처리하지 않음
//			if (quantity <= 0) {
//				continue;
//			}
//
//			BookVo book = bookService.getData(bookCode);
//
//			String bookName = book.getBookName();
//			int price = book.getPrice();
//
//			// 기존에 동일한 bookCode가 cart에 있는지 확인
//			boolean found = false;
//			for (OrderVo vo : cart) {
//				if (vo.getBookCode().equals(bookCode)) {
//					// 동일한 상품이 이미 cart에 있는 경우 수량을 증가시킴
//					vo.setQuantity(vo.getQuantity() + quantity);
//					found = true;
//					break;
//				}
//			}
//
//			if (!found) {
//				// cart에 동일한 상품이 없는 경우 새로운 OrderVo 객체를 생성하여 cart에 추가
//				OrderVo vo = new OrderVo(bookCode, bookName, quantity);
//				vo.setPrice(price);
//				vo.setBranchId(authUser.getBranchId());
//				cart.add(vo);
//			}
//		}
//
//		// 변경된 cart를 다시 세션에 저장
//		session.setAttribute("cart", cart);
//		session.setAttribute("addCart", true);
//		return "redirect:/branch/order/form"; // 처리 후 발주 기록 페이지로 리다이렉트
//	}
//
//	
//
////	지점 주문 페이지 장바구니 추가 기능
//	@PostMapping("/add")
//	public String addToCart(@RequestParam("bookCode") String bookCode, @RequestParam("quantity") int quantity,
//			HttpSession session) {
//		// 로그인 시 저장한 session authUser를 받아오는 기능
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//
//		// session에 저장된 장바구니 리스트를 받아오는 기능
//		List<OrderVo> cart = (List<OrderVo>) session.getAttribute("cart");
//
//		// jsp에서 넘어온 bookCode 기반으로 객체 저장
//		BookVo book = bookService.getData(bookCode);
//
//		// 객체에서 bookName을 뽑아와 bookName에 저장 후 주문 객체에 추가, 수량과 교재 코드도 저장
//		String bookName = book.getBookName();
//		int price = book.getPrice();
//
//		// 장바구니에 이미 같은 상품이 있는지 확인하여 수량을 증가시키거나 새로운 상품을 추가
//		boolean found = false;
//		if (cart != null) {
//			for (OrderVo vo : cart) {
//				if (vo.getBookCode().equals(bookCode)) {
//					// 동일한 상품이 이미 장바구니에 있는 경우 수량을 증가
//					vo.setQuantity(vo.getQuantity() + quantity);
//					found = true;
//					break;
//				}
//			}
//		}
//
//		// 장바구니에 동일한 상품이 없는 경우 새로운 주문 객체 생성하여 추가
//		if (!found) {
//			OrderVo vo = new OrderVo(bookCode, bookName, quantity);
//			vo.setPrice(price);
//			vo.setBranchId(authUser.getBranchId());
//
//			if (cart == null) {
//				cart = new ArrayList<>();
//			}
//			cart.add(vo);
//		}
//
//		// 변경된 장바구니를 세션에 저장
//		session.setAttribute("cart", cart);
//		session.setAttribute("addCart", true);
//		return "redirect:/branch/order/form"; // 처리 후 발주 기록 페이지로 리다이렉트
//	}
//
////	지점 주문 페이지 장바구니 삭제 기능
//	@PostMapping("/remove")
//	public String removeFromCart(@RequestParam("bookCode") String bookCode, HttpSession session) {
//
////	 	세션에서 장바구니 가져오기
//		List<OrderVo> cart = (List<OrderVo>) session.getAttribute("cart");
//		if (cart == null) {
//			cart = new ArrayList<>();
//		}
////		장바구니가 비어있지 않으면 장바구니를 순회하여 원하는 장바구니 제품을 삭제
//		if (cart != null) {
//
//			Iterator<OrderVo> iterator = cart.iterator();
//			while (iterator.hasNext()) {
//
//				OrderVo vo = iterator.next();
//
//				if (vo.getBookCode().equals(bookCode)) {
//					iterator.remove();
//					break;
//				}
//			}
//
//			// 수정된 장바구니 세션에 저장
//			session.setAttribute("cart", cart);
//		}
//		return "redirect:/branch/order/form"; // 장바구니 목록 페이지로 리다이렉트
//	}
//
//	@RequestMapping("/removeall")
//	public String removeAll(HttpSession session) {
//		session.removeAttribute("cart");
//		return "redirect:/branch/order/form";
//	}
//
//
//	@RequestMapping("/search")
//	public String searchBooks(@RequestParam("bookName") String bookName, HttpSession session, Model model) {
////		session에서 authUser 받아와 branchId와 jsp에서 넘어온 교재 이름으로
////		branch_inventory에서 데이터를 받아 리스트에 저장후 모델에 저장 
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		List<BookInventoryVo> list = bookInventoryService.search(authUser.getBranchId(), bookName);
//		model.addAttribute("list", list);
//
////		장바구니 세션 받아와 리스트에 저장후 모델에 저장
//		Object cartObject = session.getAttribute("cart");
//
//		List<OrderVo> cartList = (List<OrderVo>) cartObject;
//
//		model.addAttribute("cartList", cartList);
//		model.addAttribute("bookName", bookName);
//
//		return "branches/branch_order_form"; // 발주 페이지로 연결 (리다이렉트하면 재고 검색이 초기화됨)
//	}

	

}