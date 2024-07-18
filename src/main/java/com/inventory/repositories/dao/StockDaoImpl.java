package com.inventory.repositories.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inventory.repositories.vo.StockVo;

@Repository("StockDao")
public class StockDaoImpl implements StockDao {
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<StockVo> getStockInList(String branchId) {
		List <StockVo> list = sqlSession.selectList("stock.stockInList", branchId);
		return list;
	}

	@Override
	public List<StockVo> getStockInDetail(String inId) {
		List<StockVo>list = sqlSession.selectList("stock.stockInDetail", inId);
		return list;
	}

	@Override
	public boolean stockInCheck(String inId) {
		int successUpdate = sqlSession.update("stock.confirmStockInCheckedIn", inId);
		return successUpdate == 1;
	}

	@Override
	public boolean confirmStockIn(StockVo vo) {
		sqlSession.update("stock.confirmAndInventoryIn", vo);
		return true;
	}

	@Override
	public int getInId(String branchId) {
		return sqlSession.selectOne("stock.getStockInIdBybranchId", branchId);
	}

	@Override
	public int initialStockIn(String orderId, String branchId) {
		Map <String, String> map = new HashMap<>();
		map.put("orderId", orderId);
		map.put("branchId", branchId);
		return sqlSession.insert("stock.initialStockIn", map);
	}

	@Override
	public List<StockVo> getStockOutList(String branchId) {
		return sqlSession.selectList("stock.stockOutList", branchId);
	}
	
	@Override
	public List <StockVo> getStockOutDetail(String outId){
		return sqlSession.selectList("stock.stockOutDetail", outId);
	}

	@Override
	public int insertStockOut(String branchId) {
		return sqlSession.insert("stock.confirmAndInsertStockOut", branchId);
	}

	@Override
	public int getStockOutId(String branchId) {
		return sqlSession.selectOne("stock.getStockOutIdByBranchId", branchId);
	}

	@Override
	public int insertOutDetail(StockVo vo) {
		return sqlSession.insert("stock.insertOutDetail", vo);
	}

	@Override
	public int confirmStockOut(StockVo vo) {
		return sqlSession.update("stock.confirmAndInventoryOut", vo);
	}

}
