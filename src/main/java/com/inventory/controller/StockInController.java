package com.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inventory.repositories.vo.StockVo;
import com.inventory.repositories.vo.UserVo;
import com.inventory.services.StockService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/stock")
@Controller
public class StockInController {
	
	@Autowired
	StockService stockService;

	@RequestMapping("/in/list")
	public String stockInList(Model model, HttpSession session) {
		UserVo vo = (UserVo) session.getAttribute("authUser");
		List <StockVo> list = stockService.getStockInList(vo.getBranchId());
		model.addAttribute("list", list);
		return "branches/branch_stock_in_list";
	}
	
	@RequestMapping("/in/{id}/detail")
	public String stockIndetail(@PathVariable ("id") String inId, Model model) {
		List<StockVo> list = stockService.getStockInDetail(inId);
		model.addAttribute("list", list);
		return "branches/branch_stock_in_detail";
	}
}
