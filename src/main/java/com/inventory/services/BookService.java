package com.inventory.services;

import java.util.List;

import com.inventory.repositories.vo.BookVo;

public interface BookService {
	public List<BookVo> getbookList();

	public List<BookVo> search(String bookName);

	public boolean writebook(BookVo vo);

	public boolean deletebook(String bookCode);
	
	public boolean updatebook(BookVo vo);
	
	public BookVo getData(String bookCode);
	
	public int insertInventory(String bookCode);
}
