package com.inventory.repositories.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inventory.repositories.vo.BookVo;


@Repository("BookDao")
public class BookDaoImpl implements BookDao{


	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<BookVo> selectAll() {
		List<BookVo> list = sqlSession.selectList("book.selectAll");
		System.out.println(list);
		return list;
	}

	@Override
	public int insert(BookVo vo) {
		
		return sqlSession.insert("book.insert", vo);
	}

	@Override
	public int delete(String bookCode) {
	    System.out.println("service" + bookCode + "count");
	    return sqlSession.delete("book.delete", bookCode);
	}


	@Override
	public int update(BookVo vo) {
		
		return sqlSession.update("book.updateData", vo);
	}

	@Override
	public List<BookVo> search(String bookName) {
		List<BookVo> list = sqlSession.selectList("book.searchList", bookName);
		System.out.println("dao" + list);
		return list;
	}

	@Override
	public BookVo getData(String bookCode) {
		BookVo vo = sqlSession.selectOne("book.getData",bookCode);
		return vo;
	}

	
	
	
}
