package com.inventory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventory.repositories.vo.BookInventoryVo;
import com.inventory.repositories.vo.UserVo;
import com.inventory.services.BookInventoryService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/branch")
@Controller
public class BranchController {
	
	@Autowired
	private BookInventoryService bookInvenService;
	
	@RequestMapping({"/inventory", "/home"})
	public String branchHome(HttpSession session, RedirectAttributes redirectAttributes,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value="check", required = false) String check,
			@RequestParam(value = "orderBy", defaultValue = "CASE WHEN inventory > 0 THEN 1 ELSE 2 END ASC, kindcode ASC") String orderBy,
			Model model) {
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		if (!("1").equals(authUser.getAuthCode())) {
//			//	홈화면으로 보내
//			redirectAttributes.addFlashAttribute("errorMsg", "auth code 불일치 ");
//			return "redirect:/";
//		}
//		if (check != null && check.equals("check")) {
//			if (keyword != null && !keyword.isEmpty()) {
//				List<BookInventoryVo> list = bookInvenService.checkedSearch(authUser.getBranchId(), keyword);
//				model.addAttribute("list", list);
//			} else {
//				List<BookInventoryVo> list = bookInvenService.checkedGetList(authUser.getBranchId());
//				model.addAttribute("list", list);
//			}
//			
//		} else {
//			if (keyword != null && !keyword.isEmpty()) {
//				List<BookInventoryVo> list = bookInvenService.search(authUser.getBranchId(), keyword);
//				model.addAttribute("list", list);
//			} else {
//				List<BookInventoryVo> list = bookInvenService.getList(authUser.getBranchId());
//				model.addAttribute("list", list);
//			}
//		}
		
		Map <String, Object> params = new HashMap<>();
		params.put("branchId", authUser.getBranchId());
	    params.put("keyword", keyword != null ? keyword : "");
	    params.put("check", check);
	    params.put("orderBy", orderBy != null ? orderBy.trim() : null);

		model.addAttribute("list", bookInvenService.invenList(params));
		
//		session.setAttribute("authUser", authUser);
		
		return "branches/branch_home";
	}
}
