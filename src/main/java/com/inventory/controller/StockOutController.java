package com.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inventory.repositories.vo.BookInventoryVo;
import com.inventory.repositories.vo.StockVo;
import com.inventory.repositories.vo.UserVo;
import com.inventory.services.BookInventoryService;
import com.inventory.services.StockService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/branch/stockout")
public class StockOutController {

	@Autowired
	StockService stockService;
	@Autowired
	BookInventoryService bookInvenService;
    
    @RequestMapping("/list")
	public String stockInList(Model model, HttpSession session) {
		UserVo vo = (UserVo) session.getAttribute("authUser");
		List <StockVo> list = stockService.getStockOutList(vo.getBranchId());
		model.addAttribute("list", list);
		return "branches/branch_stock_out_list";
	}
    
    @RequestMapping("/form")
    public String moveToStockOutFrom(HttpSession session, Model model) {
    	UserVo vo = (UserVo)session.getAttribute("authUser");
		session.setAttribute("authUser", vo);
		return "branches/branch_stock_out_form";
    }
    
    @RequestMapping(value = "/getListForform", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<BookInventoryVo> getListForform(HttpSession session) {
        UserVo vo = (UserVo) session.getAttribute("authUser");
        return bookInvenService.checkedGetList(vo.getBranchId());
    }
    
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<BookInventoryVo> search(HttpSession session, @RequestParam("keyword") String keyword) {
        UserVo vo = (UserVo) session.getAttribute("authUser");
        List<BookInventoryVo> list;
        if (keyword != null && !keyword.trim().isEmpty()) {
            list = bookInvenService.checkedSearch(vo.getBranchId(), keyword);
        } else {
            list = bookInvenService.checkedGetList(vo.getBranchId());
        }
        return list;
    }
    
    @RequestMapping("/confirm")
    public ResponseEntity<String> confirmStockOut(HttpSession session, @RequestBody List<StockVo> vo) {
        if (vo == null || vo.isEmpty()) {
            // 빈 데이터가 넘어왔을 때 오류 응답 반환
            return new ResponseEntity<>("전송된 데이터가 없습니다.", HttpStatus.BAD_REQUEST);
        }

        UserVo userVo = (UserVo)session.getAttribute("authUser");
        String branchId = userVo.getBranchId();
        
        stockService.insertStockOut(branchId);
        int outId = stockService.getStockOutId(branchId);
        
        vo.forEach(item -> {
            item.setId(outId);
            item.setBranchId(branchId);
            stockService.insertOutDetail(item);
            stockService.confirmStockOut(item);
        });
        
        return new ResponseEntity<>("성공적으로 처리되었습니다.", HttpStatus.OK);
    }
    
    @RequestMapping ("/detail/{outId}")
    public String stockOutDetail(@PathVariable ("outId") String outId, Model model) {
    	List <StockVo> list = stockService.getStockOutDetail(outId);
    	model.addAttribute("list", list);
    	return "branches/branch_stock_out_detail";
    }
}
