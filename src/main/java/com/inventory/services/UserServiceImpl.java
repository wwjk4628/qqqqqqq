package com.inventory.services;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inventory.repositories.dao.UserDao;
import com.inventory.repositories.vo.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public boolean join(UserVo vo) {
		
		// 비밀번호를 인코딩하여 저장
		String encodedPassword = passwordEncoder.encode(vo.getPassword());
		vo.setPassword(encodedPassword);
		
		int insertedCount = 0;
		insertedCount = userDao.insert(vo);
		return insertedCount == 1;
	}

	@Override
	public UserVo getUser(String name) {
		UserVo userVo = userDao.selectUser(name);
		return userVo;
	}

	@Override
	public UserVo getUser(String name, String password) {
		UserVo userVo = userDao.selectUser(name, password);
		return userVo;
	}

	@Override
	public boolean isAuthenticated(HttpServletRequest request) {
		//	이거 딱히 필요 없을 수도
		HttpSession session = request.getSession(false);
		
		if (session != null) {	//	인증했을 가능성이 있음
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			return authUser != null;
		}
		
		return false;
	}
	
	@Override
	public List<UserVo> getList() {
		List<UserVo> list = userDao.getList();
		return list;
	}

	@Override
	public long userCount() {
		long count = userDao.getCount();
		return count;
	}

	@Override
	public boolean delete(long no) {
		int deleteCount = userDao.delete(no); 
		return deleteCount == 1;
	}

	@Override
	public boolean confirm(int no, int id) {
		int updateCount = userDao.confirm(no, id);
		return updateCount == 1;
	}

	@Override
	public UserVo getUserByNameForLogin(String username) {
		return userDao.findByUsernameForLogin(username);
	}

	@Override
	public List<UserVo> getAllBranches() {
		return userDao.getAllBranches();
	}
	
}
