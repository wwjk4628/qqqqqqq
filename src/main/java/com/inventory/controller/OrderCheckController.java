package com.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventory.repositories.vo.OrderVo;
import com.inventory.repositories.vo.StockVo;
import com.inventory.repositories.vo.UserVo;
import com.inventory.services.OrderCheckService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin/ordercheck")
@Controller
public class OrderCheckController {

	@Autowired
	private OrderCheckService OrderCheckService;

	@RequestMapping({ "", "/", "/list" })
	public String orderCheckList(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		// 로그인 정보 판단
		if (!("2").equals(authUser.getAuthCode())) {
			// 로그인 안 한 경우 홈으로 리다이렉트
			redirectAttributes.addFlashAttribute("errorMsg", "auth code 불일치 ");
			return "redirect:/";
		}

		session.setAttribute("authUser", authUser);
		List<OrderVo> list = OrderCheckService.getList();

		List<OrderVo> branchList = OrderCheckService.getBranchList();

		model.addAttribute("branchList", branchList);

		model.addAttribute("list", list);

		return "admins/order_check_list";
	}

	// 지점 별 오더 확인 위한 메서드
	@RequestMapping("/list/{no}")
	public String branchOrderCheckList(HttpSession session, RedirectAttributes redirectAttributes, Model model,
			@PathVariable("no") String no) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (!("2").equals(authUser.getAuthCode())) {
			// 로그인 안 한 경우 홈으로 리다이렉트
			redirectAttributes.addFlashAttribute("errorMsg", "auth code 불일치 ");
			return "redirect:/";
		}

		List<OrderVo> list = OrderCheckService.getBranchsList(no);
		List<OrderVo> branchList = OrderCheckService.getBranchList();

		model.addAttribute("branchList", branchList);
		model.addAttribute("list", list);

		return "admins/order_check_list";
	}

	@RequestMapping("/detail/{id}")
	public String orderCheckdetail(@PathVariable("id") String id, HttpSession session, Model model) {

		List<OrderVo> list = OrderCheckService.getOrderDetail(id);
		
		String checked = null;
		for(OrderVo vo:list) {
			checked = vo.getChecked();
		}
		
		session.setAttribute("list", list);
		model.addAttribute("checked", checked);

		return "admins/order_check_detail";
	}

	@RequestMapping("/refuse/{id}")
	public String orderRefuse(@PathVariable("id") String id) {
		OrderCheckService.refuseOrder(id);
		return "redirect:/admin/ordercheck/list";
	}

	@RequestMapping("/confirm/{id}")
	public String orderConfirm(@PathVariable("id") String id) {
		OrderCheckService.confirmOrderCode(id);

		String branchId = OrderCheckService.getBranchId(id);
		OrderCheckService.confirmAndInsertStockIn(id, branchId);

		int inId = OrderCheckService.getStockIn(id);

		List<OrderVo> list = OrderCheckService.getOrderDetail(id);
		for (OrderVo vo : list) {
			StockVo stockVo = new StockVo(inId, vo.getBookCode(), vo.getQuantity());
			OrderCheckService.confirmAndInsertInDetail(stockVo);
		}

		return "redirect:/admin/ordercheck/list";
	}
}
