package com.inventory.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inventory.repositories.vo.BookVo;
import com.inventory.services.BookService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin/book")
@Controller
public class BookController {

	@Autowired
	BookService bookService;

	@RequestMapping("/list")
	public String booklist(Model model, HttpSession session) {
//		book_list 테이블에서 전체 데이터를 뽑아와 list에 저장
		List<BookVo> list = bookService.getbookList();
		
		Boolean addList = (Boolean)session.getAttribute("addList");
		
		if (addList != null && addList) {
			model.addAttribute("addList", true);
			session.removeAttribute("addList");
		}
//		모델에 책 목록을 추가하여 JSP에 전달
		model.addAttribute("list", list);
		return "admins/book_update";
	}

	@RequestMapping("/delete/{bookCode}")
	public String delete(@PathVariable("bookCode") String bookCode) {
//		book_list 테이블에서 부분 데이터 삭제 기능
		bookService.deletebook(bookCode);
		return "redirect:/admin/book/list";
	}

//	본사 교재 리스트에서 교재 추가 기능
	@PostMapping("/insert")
	public String insertBook(@ModelAttribute BookVo vo, Model model, HttpSession session) {
		List<BookVo> list = bookService.getbookList();
		model.addAttribute("list", list);

		// 중복 검사
		boolean isDuplicate = checkForDuplicates(list, vo.getBookCode());

		if (isDuplicate) {
			model.addAttribute("error", "교재ID 중복");
			return "admins/book_update";
		} 
		// 중복되지 않으면 책 정보 추가
		boolean success = (boolean)bookService.writebook(vo);
		if (success) {
			session.setAttribute("addList", true);
		}
		return "redirect:/admin/book/list";
	}

	private boolean checkForDuplicates(List<BookVo> list, String bookCode) {
		// list에서 bookCode와 동일한 것이 있는지 검사
		List<BookVo> duplicates = list.stream().filter(book -> book.getBookCode().equals(bookCode))
				.collect(Collectors.toList());

		return !duplicates.isEmpty();
	}



//	본사 교재 리스트에서 교재 검색 기능
	@GetMapping("/search")
	public String searchBooks(@RequestParam("bookName") String bookName, Model model) {
//		book_list 테이블의 데이터 검색 기능
		List<BookVo> list = bookService.search(bookName);
		model.addAttribute("list", list);
		return "admins/book_update";
	}

	@GetMapping("/update/{bookCode}")
	public String updateBooks(@PathVariable("bookCode") String bookCode, Model model) {
//		bookCode 기반으로 book_list 테이블의 데이터를 받아와 모델에 저장
		BookVo vo = bookService.getData(bookCode);
		model.addAttribute("vo", vo);
		return "admins/book_modify";
	}

//	본사 교재 리스트중 교재 정보 수정
	@PostMapping("/modify")
	public String modify(@ModelAttribute BookVo vo) {
//		model을 받아와 book_list테이블의 부분 데이터를 수정
		bookService.updatebook(vo);
		return "redirect:/admin/book/list";
	}
}
