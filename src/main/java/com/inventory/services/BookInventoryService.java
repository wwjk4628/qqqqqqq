package com.inventory.services;

import java.util.List;
import java.util.Map;

import com.inventory.repositories.vo.BookInventoryVo;

public interface BookInventoryService {

	public List <BookInventoryVo> getList (String id);
	public List <BookInventoryVo> search (String id, String keyword, String key, String ordering);
	
	public int getInventory(BookInventoryVo vo);
	
	public List <BookInventoryVo> checkedGetList(String id);
	public List <BookInventoryVo> checkedSearch (String id, String keyword);
	
	public List <BookInventoryVo> invenList (Map<String, Object> params);
}
