package com.inventory.repositories.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.inventory.repositories.vo.UserVo;

public interface UserDao {
	//	가입
	public int insert(UserVo vo);
	//	아이디 중복 체크 로직
	public UserVo selectUser(String name);
	
	public UserVo findByUsernameForLogin(@Param("username") String username);
	//	로그인
	public UserVo selectUser(String name, String password);
	//	유저 목록
	public List<UserVo> getList();
	//	승인 대기 유저 수
	public long getCount();
	//	계정 삭제
	public int delete(long no);
	//	계정 승인 (auth_code 수정)
	public int confirm(int no, int id);
	//	지점 목록
	public List<UserVo> getAllBranches();
	//	프로필 정보 로드
	public UserVo selectUserForProfile(String name);
	//	비밀번호 변경
	public void updatePassword(String name, String newPassword);
	
}
