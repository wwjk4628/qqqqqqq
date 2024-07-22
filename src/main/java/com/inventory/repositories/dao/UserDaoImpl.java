package com.inventory.repositories.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inventory.exceptions.UserDaoException;
import com.inventory.repositories.vo.BookInventoryVo;
import com.inventory.repositories.vo.BookVo;
import com.inventory.repositories.vo.UserVo;

@Repository("userDao")
public class UserDaoImpl implements UserDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int insert(UserVo vo) {
		try {
			return sqlSession.insert("users.insert", vo);
		} catch(Exception e) {
			e.printStackTrace();
			throw new UserDaoException("회원 가입 중 문제", vo);
		}	
	}

	@Override
	public UserVo selectUser(String name) {
		UserVo userVo = sqlSession.selectOne("users.selectUserByName", name);
		return userVo;
	}
	
	@Override
	public UserVo findByUsernameForLogin(@Param("username")String username) {
	    return sqlSession.selectOne("users.selectUserByNameForLogin", username);
	}
	
	@Override
	public UserVo selectUser(String name, String password) {
		//	mybatis parameterType =map 으로 전달하기
		Map<String, String> userMap = new HashMap<>();
		userMap.put("name", name);
		userMap.put("password", password);

		UserVo userVo = sqlSession.selectOne("users.selectUserByNameAndPassword", userMap);
		return userVo;
	}

	@Override
	public List<UserVo> getList() {
		List <UserVo> list = sqlSession.selectList("users.selectUserList");
		return list;
	}

	@Override
	public long getCount() {
		long count = sqlSession.selectOne("users.countUserCode0");
		return count;
	}

	@Override
	public int delete(long no) {
		try {
			return sqlSession.delete("users.deleteUserAcount", no);
		} catch(Exception e) {
			e.printStackTrace();
			throw new UserDaoException("계정 삭제 중", no);
		}
	}

	@Override
	public int confirm(int no, int id) {
		
		Map<String, Integer> confirmMap = new HashMap<>();
		confirmMap.put("no", no);	//	user_id
		confirmMap.put("authCode", 1);	//	auth code를 1로 수정 -> 지점 담당자

		List <BookInventoryVo> invenList = sqlSession.selectList("bookInventory.selectInventory", Integer.toString(id));	//	해당 브랜치 인벤토리 받아오기
		
		if (invenList != null && !invenList.isEmpty()) {	// 인벤토리가 널이 아니면?
			
			return sqlSession.update("users.confirmUserAcount", confirmMap);	// 그냥 계정 승인
		
		} else {																// null이거나 비어있으면 북리스트로 인벤토리 생성
			List <BookVo> list = sqlSession.selectList("book.selectAll");
			
			//	인벤토리 초기화 로직
			for (BookVo bookVo :list) {
				Map <String, String> inventoryMap = new HashMap<>();
				String branchId = String.valueOf(id);
				inventoryMap.put("bookCode", bookVo.getBookCode());
				inventoryMap.put("id", branchId);		// branch_id
				sqlSession.insert("bookInventory.initialization", inventoryMap);
			}
			
		}
		//	관리자 계정으로 수정하기 -> 1을 2로 바꾸는 장치
		return sqlSession.update("users.confirmUserAcount", confirmMap);
	}

	@Override
	public List<UserVo> getAllBranches() {
		List <UserVo> list = sqlSession.selectList("users.selectBranchList");
		return list;
	}
}
