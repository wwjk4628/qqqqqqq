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
	public List<BookInventoryVo> search(String id, String keyword, String key, String ordering) {
		Map <String, String> map = new HashMap<>();
		System.err.println("dao" + key + ordering);
		map.put("id", id);
		map.put("keyword", keyword);
		map.put("key", key);
		map.put("ordering", ordering);
		List <BookInventoryVo> list = sqlSession.selectList("bookInventory.searchInventory", map);
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

	@Override
	public List<BookInventoryVo> sumStockOut(Map<String, Object> params) {
		return sqlSession.selectList("bookInventory.outInventorySum", params);
	}

	@Override
	public String getBranchName(String branchId) {
		return sqlSession.selectOne("bookInventory.getBranchNameByBranchId", branchId);
	}

	@Override
	public List<BookInventoryVo> sumStockInBefore(Map<String, Object> params) {
		return sqlSession.selectList("bookInventory.shikanokonokonoko", params);
	}

	@Override
	public List<BookInventoryVo> sumStockOutBefore(Map<String, Object> params) {
		return sqlSession.selectList("bookInventory.koshitanntann", params);
	}
}
