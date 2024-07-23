package com.inventory.repositories.dao;

import java.util.List;
import java.util.Map;

import com.inventory.repositories.vo.BookVo;

public interface BookDao {
	public List<BookVo> selectAll();	//	게시물 목록
	public List<BookVo> search(String bookName);	//	게시물 목록
	public BookVo getData(String bookCode);
	public int insert(BookVo vo);	//	게시물 작성
	public int delete(String bookCode);	//	게시물 삭제
	public int update(BookVo vo);	//	게시물 수정
	
	public List<String> branchIdList();
	public int insertInventory(Map<String, String> map);
}
