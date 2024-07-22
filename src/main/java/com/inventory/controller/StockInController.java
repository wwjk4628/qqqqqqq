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

@RequestMapping("/branch/stockin")
@Controller
public class StockInController {
	
	@Autowired
	StockService stockService;

	@RequestMapping("/list")
	public String stockInList(Model model, HttpSession session) {
		UserVo vo = (UserVo) session.getAttribute("authUser");
		List <StockVo> list = stockService.getStockInList(vo.getBranchId());
		model.addAttribute("list", list);
		return "branches/branch_stock_in_list";
	}
	
	@RequestMapping("/detail/{id}")
	public String stockIndetail(@PathVariable ("id") String inId, Model model) {
		List<StockVo> list = stockService.getStockInDetail(inId);
		
		String i = null;
		for(StockVo vo:list) {
			i = vo.getCheckedIn();
		}
		model.addAttribute("check", i);
		model.addAttribute("list", list);
		model.addAttribute("inId", inId);
		return "branches/branch_stock_in_detail";
	}
	
	@RequestMapping("/confirm/{id}")
	public String confirmStockIn(@PathVariable ("id") String inId) {
		stockService.stockInCheck(inId);
		List<StockVo>list = stockService.getStockInDetail(inId);
		
		for (StockVo vo :list) {
			StockVo insertVo = new StockVo (vo.getBranchId(), vo.getBookCode(), vo.getQuantity());
			stockService.confirmStockIn(insertVo);
		}
		
		return "redirect:/branch/inventory";
	}
}
