package com.inventory.services;

import java.util.List;

import com.inventory.repositories.vo.UserVo;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
	//	가입
	public boolean join(UserVo vo);
	//	아이디로 유저 정보 검색
	public UserVo getUser(String name);
	
	public UserVo getUserByNameForLogin(String username);
	
	//	로그인
	public UserVo getUser(String name, String password);
	//	유저 리스트
	public List<UserVo> getList();
	//	승인 대기 유저 수
	public long userCount();
	//	계정 삭제
	public boolean delete(long no);
	//	계정 승인
	public boolean confirm(int no, int id);
	
	//	인증 체크 메서드 
	public boolean isAuthenticated(HttpServletRequest request);
	//	지점 리스트
	public List<UserVo> getAllBranches();
	//	유저 프로필
	public UserVo getUserProfile(String name);
	//	비밀번호 변경
	public boolean changePassword(String name, String currentPassword, String newPassword);
	//	임시 비밀번호로 업데이트
	public void updatePassword(String name, String password);
	//	임시 비밀번호 전송
	public void sendEmail(String to, String subject, String text);
	public void resetPassword(String username);
}
