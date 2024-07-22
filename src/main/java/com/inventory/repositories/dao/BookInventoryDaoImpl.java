package com.inventory.repositories.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inventory.repositories.vo.BookInventoryVo;

@Repository("BookInventoryDao")
public class BookInventoryDaoImpl implements BookInventoryDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<BookInventoryVo> list(String id) {
		List <BookInventoryVo> list = sqlSession.selectList("bookInventory.selectInventory", id);
		return list;
	}

	@Override
	public List<BookInventoryVo> search(String id, String keyword) {
		Map <String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("keyword", keyword);
		List <BookInventoryVo> list = sqlSession.selectList("bookInventory.searchInventory", map);
		return list;
	}

	@Override
	public List<BookInventoryVo> checkedList(String id) {
		List<BookInventoryVo> list = sqlSession.selectList("bookInventory.selectInventoryCheck", id);
		return list;
	}

	@Override
	public List<BookInventoryVo> checkedSearch(String id, String keyword) {
		Map <String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("keyword", keyword);
		List <BookInventoryVo> list = sqlSession.selectList("bookInventory.searchInventoryCheck", map);
		return list;
	}

	@Override
	public int getInventory(BookInventoryVo vo) {
		int inventory = sqlSession.selectOne("bookInventory.getInventory", vo);
		return inventory;
	}

	@Override
	public List<BookInventoryVo> invenList(Map<String, Object> params) {
		List <BookInventoryVo> list = sqlSession.selectList("bookInventory.dynamicInvenList", params);
		return list;
	}

	@Override
	public List<BookInventoryVo> sumStockIn(Map<String, Object> params) {
		return sqlSession.selectList("bookInventory.inInventorySum", params);
	}
}
