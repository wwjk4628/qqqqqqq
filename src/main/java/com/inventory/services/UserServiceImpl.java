package com.inventory.services;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private JavaMailSender mailSender;

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

	@Override
	public UserVo getUserProfile(String name) {
		return userDao.selectUserForProfile(name);
	}
	
	@Override
	public boolean changePassword(String name, String currentPassword, String newPassword) {
	    UserVo user = userDao.findByUsernameForLogin(name);

	    if (user == null || !passwordEncoder.matches(currentPassword, user.getPassword())) {
	        return false;
	    }

	    String encodedNewPassword = passwordEncoder.encode(newPassword);
	    userDao.updatePassword(name, encodedNewPassword);
	    return true;
	}

    @Override
    public void updatePassword(String username, String password) {
        userDao.updatePassword(username, password);
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
    	 
    	if (to == null || to.isEmpty()) {
    	    throw new IllegalArgumentException("Recipient email address must not be null or empty");
    	}
        SimpleMailMessage message = new SimpleMailMessage();        
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
    
    @Override
    public void resetPassword(String username) {
        UserVo userVo = userDao.findByUsernameForLogin(username);
        if (userVo == null) {
            throw new RuntimeException("User not found");
        }
        String tempPassword = UUID.randomUUID().toString().substring(0, 8);
        updatePassword(username, tempPassword);
        if (userVo.getEmail() != null && !userVo.getEmail().isEmpty()) {
            sendEmail(userVo.getEmail(), "Temporary Password", "Your new temporary password is: " + tempPassword);
        } else {
            throw new RuntimeException("Email address is missing for user: " + username);
        }
    }

	
}
