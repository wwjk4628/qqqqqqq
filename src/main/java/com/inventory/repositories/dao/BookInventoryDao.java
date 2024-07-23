package com.inventory.repositories.dao;

import java.util.List;
import java.util.Map;

import com.inventory.repositories.vo.BookInventoryVo;

public interface BookInventoryDao {
	
	public List <BookInventoryVo> list(String id);
	public List <BookInventoryVo> search(String id, String keyword, String key, String ordering);
	
	public int getInventory(BookInventoryVo vo);
	
	public List <BookInventoryVo> invenList(Map <String, Object> params);
	
	public List <BookInventoryVo> sumStockIn(Map<String, Object> params);
	public List <BookInventoryVo> sumStockOut(Map <String, Object> params);
	
	public List <BookInventoryVo> sumStockInBefore(Map<String, Object> params);
	public List <BookInventoryVo> sumStockOutBefore(Map<String, Object> params);
	
	public String getBranchName(String branchId);
}
