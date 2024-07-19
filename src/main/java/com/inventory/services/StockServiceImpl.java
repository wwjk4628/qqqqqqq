package com.inventory.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.repositories.dao.StockDao;
import com.inventory.repositories.vo.StockVo;

@Service("StockService")
public class StockServiceImpl implements StockService {

	@Autowired
	StockDao stockDao;
	
	@Override
	public List<StockVo> getStockInList(String branchId) {
		List<StockVo>list = stockDao.getStockInList(branchId); 
		return list;
	}

	@Override
	public List<StockVo> getStockInDetail(String inId) {
		List<StockVo>list = stockDao.getStockInDetail(inId);
		return list;
	}
	
	@Override
	public boolean stockInCheck(String inId) {
		return stockDao.stockInCheck(inId);
	}

	@Override
	public boolean confirmStockIn(StockVo vo) {
		return stockDao.confirmStockIn(vo);
	}

	@Override
	public int getInId(String branchId) {
		return stockDao.getInId(branchId);
	}

	@Override
	public int initialStockIn(String orderId, String branchId) {
		return stockDao.initialStockIn(orderId, branchId);
	}

	@Override
	public List<StockVo> getStockOutList(String branchId) {
		return stockDao.getStockOutList(branchId);
	}
	
	@Override
	public List<StockVo> getStockOutDetail(String outId) {
		return stockDao.getStockOutDetail(outId);
	}

	@Override
	public int insertStockOut(String branchId) {
		return stockDao.insertStockOut(branchId);
	}

	@Override
	public int getStockOutId(String branchId) {
		return stockDao.getStockOutId(branchId);
	}

	@Override
	public int insertOutDetail(StockVo vo) {
		return stockDao.insertOutDetail(vo);
	}

	@Override
	public int confirmStockOut(StockVo vo) {
		return stockDao.confirmStockOut(vo);
	}
}
