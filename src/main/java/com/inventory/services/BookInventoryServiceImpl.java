package com.inventory.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	public List<BookInventoryVo> search(String id, String keyword) {
		return bookInventoryDao.search(id, keyword);
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
		List<BookInventoryVo> sumStockInInventory = bookInventoryDao.sumStockIn(params);
		List <BookInventoryVo> inventoryList = bookInventoryDao.invenList(params);
		
		Map<String, Integer> sumStockMap = sumStockInInventory.stream().collect(Collectors.toMap(BookInventoryVo::getBookCode, BookInventoryVo::getInventory));

		for (BookInventoryVo inventory : inventoryList) {
	        Integer sumQuantity = sumStockMap.get(inventory.getBookCode());
	        if (sumQuantity != null) {
	            inventory.setSumInInventory(sumQuantity);
	        }
	    }
		
		List<BookInventoryVo> sumStockOutInventory = bookInventoryDao.sumStockOut(params);
		sumStockMap = sumStockOutInventory.stream().collect(Collectors.toMap(BookInventoryVo::getBookCode, BookInventoryVo::getInventory));
		for (BookInventoryVo inventory : inventoryList) {
	        Integer sumQuantity = sumStockMap.get(inventory.getBookCode());
	        if (sumQuantity != null) {
	            inventory.setSumOutInventory(sumQuantity);
	        }
	    }
		return inventoryList;
	}

}
