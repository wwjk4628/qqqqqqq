package com.inventory.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inventory.repositories.dao.BookDao;
import com.inventory.repositories.vo.BookVo;



@Repository("BookService")
public class BookServiceImpl implements BookService{

	@Autowired
	BookDao bookDao;

	@Override
	public List<BookVo> getbookList() {
		List<BookVo> list = bookDao.selectAll();
		System.out.println(list);
		return list;
	}

	@Override
	public boolean writebook(BookVo vo) {
		int insertedCount = bookDao.insert(vo);
		return 1 == insertedCount;
	}

	@Override
	public boolean deletebook(String bookCode) {
		int deletedCount = bookDao.delete(bookCode);
		System.out.println("service" + bookCode + "count" + deletedCount);
		return 1 == deletedCount;
	}

	@Override
	public boolean updatebook(BookVo vo) {
		int updatedCount = bookDao.update(vo);
		return 1 == updatedCount;
	}

	@Override
	public List<BookVo> search(String bookName) {
		System.out.println("ser" + bookName);
		List<BookVo> list = bookDao.search(bookName);
		return list;
	}

	@Override
	public BookVo getData(String bookCode) {
		BookVo vo = bookDao.getData(bookCode);
		return vo;
	}

	@Override
	public int insertInventory(String bookCode) {
		List <String> branchIdList = bookDao.branchIdList();
		
		for (String id: branchIdList) {
			Map <String, String> map = new HashMap<>();
			map.put("id", id);
			map.put("bookCode", bookCode);
			bookDao.insertInventory(map);
		}
		
		return 1;
	}

}
