package com.inventory.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.repositories.dao.BookInventoryDao;
import com.inventory.repositories.vo.BookInventoryVo;

@Service("BookInventoryService")
public class BookInventoryServiceImpl implements BookInventoryService {
	
	@Autowired
	private BookInventoryDao bookInventoryDao;

	@Override
	public List<BookInventoryVo> getList(String id) {
		return bookInventoryDao.list(id);
	}

	@Override
	public List<BookInventoryVo> search(String id, String keyword, String key, String ordering) {
		return bookInventoryDao.search(id, keyword, key, ordering);
	}

	@Override
	public List<BookInventoryVo> checkedGetList(String id) {
		return bookInventoryDao.checkedList(id);
	}

	@Override
	public List<BookInventoryVo> checkedSearch(String id, String keyword) {
		return bookInventoryDao.checkedSearch(id, keyword);
	}

	@Override
	public int getInventory(BookInventoryVo vo) {
		int inventory = bookInventoryDao.getInventory(vo);
		return inventory;
	}

	@Override
	public List<BookInventoryVo> invenList(Map<String, Object> params) {
		return bookInventoryDao.invenList(params);
	}

	
}
