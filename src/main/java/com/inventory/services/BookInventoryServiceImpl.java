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
	public List<BookInventoryVo> search(String id, String keyword, String key, String ordering) {
		return bookInventoryDao.search(id, keyword, key, ordering);
	}

	@Override
	public int getInventory(BookInventoryVo vo) {
		int inventory = bookInventoryDao.getInventory(vo);
		return inventory;
	}

	@Override
	public List<BookInventoryVo> invenList(Map<String, Object> params) {
		List <BookInventoryVo> inventoryList = bookInventoryDao.invenList(params);
		
		if(params.get("startDate") != null) {
			List <BookInventoryVo> sumStockInBeforeList = bookInventoryDao.sumStockInBefore(params);
			List <BookInventoryVo> sumStockOutBeforeList = bookInventoryDao.sumStockOutBefore(params);
			
			Map<String, Integer> sumStockInMap = sumStockInBeforeList.stream().collect(Collectors.toMap(BookInventoryVo::getBookCode, BookInventoryVo::getInventory));
			Map<String, Integer> sumStockOutMap = sumStockOutBeforeList.stream().collect(Collectors.toMap(BookInventoryVo::getBookCode, BookInventoryVo::getInventory));
			
			for (BookInventoryVo inventory : inventoryList) {
				Integer inQuantity = sumStockInMap.get(inventory.getBookCode());
				if (inQuantity != null) {
		            inventory.setStartInventory(inQuantity);
		        }
				Integer outQuantity = sumStockOutMap.get(inventory.getBookCode());
				if (outQuantity != null) {
					inventory.setStartInventory(inventory.getStartInventory() - outQuantity);
				}
			}
		}
		
		List<BookInventoryVo> sumStockInInventory = bookInventoryDao.sumStockIn(params);
		Map<String, Integer> sumStockInMap = sumStockInInventory.stream().collect(Collectors.toMap(BookInventoryVo::getBookCode, BookInventoryVo::getInventory));
		List<BookInventoryVo> sumStockOutInventory = bookInventoryDao.sumStockOut(params);
		Map<String, Integer> sumStockOutMap = sumStockOutInventory.stream().collect(Collectors.toMap(BookInventoryVo::getBookCode, BookInventoryVo::getInventory));
		
		for (BookInventoryVo inventory : inventoryList) {
	        Integer sumInQuantity = sumStockInMap.get(inventory.getBookCode());
	        if (sumInQuantity != null) {
	            inventory.setSumInInventory(sumInQuantity);
	        }
	        Integer sumOutQuantity = sumStockOutMap.get(inventory.getBookCode());
	        if (sumOutQuantity != null) {
	            inventory.setSumOutInventory(sumOutQuantity);
	        }
	    }
		
		return inventoryList;
	}

	@Override
	public String getBranchName(String branchId) {
		return bookInventoryDao.getBranchName(branchId);
	}

}