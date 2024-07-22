package com.inventory.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inventory.repositories.vo.UserVo;
import com.inventory.services.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo userVo = userService.getUserByNameForLogin(username);

        if (userVo == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
//        System.out.println("Loaded User"+userVo);
//        
//        String rawPassword = "1234";
//        boolean matches1 = passwordEncoder.matches(rawPassword, userVo.getPassword());
//        
//        String encodedPassword = passwordEncoder.encode(rawPassword);
//        boolean matches2 = passwordEncoder.matches(rawPassword, encodedPassword);
//        
//        
//        System.out.println("Raw Password: " + rawPassword);
//        System.out.println("Encoded Password: " + encodedPassword);
//        System.out.println("DB.PW: "+userVo.getPassword());
//        System.out.println("rawPassword VS DB.PW"+matches1);
//        System.out.println("rawPassword VS encoded.PW"+matches2);
//        
        String role;
        if ("2".equals(userVo.getAuthCode())) {
            role = "ADMIN";
        } else if ("1".equals(userVo.getAuthCode())) {
            role = "USER";
        } else if ("0".equals(userVo.getAuthCode())) {
        	role = "GUEST";
        } else {
            throw new BadCredentialsException("No valid role assigned to this user");
        }

        return User.builder()
                .username(userVo.getName())
                .password(userVo.getPassword())
                .roles(role)
                .build();
    }
    
// // 비밀번호 비교를 위한 메서드 추가
//    public boolean matchPassword(String rawPassword, String encodedPassword) {
//        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
//        // 로그로 rawPassword와 encodedPassword를 비교한 결과를 남김
//        System.out.println("Raw Password: " + rawPassword);
//        System.out.println("Encoded Password: " + encodedPassword);
//        System.out.println("Passwords match: " + matches);
//        return matches;
//    }
}
