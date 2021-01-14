package com.myblog.myblog.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myblog.myblog.dao.UserRepository;
import com.myblog.myblog.entity.User;
import com.myblog.myblog.utils.MD5Utils;

@Component
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User checkuser(String username, String password) {
		
		User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
		
		return user;
	}

}
