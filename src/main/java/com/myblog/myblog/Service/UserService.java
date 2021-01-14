package com.myblog.myblog.Service;

import com.myblog.myblog.entity.User;

public interface UserService {
	
	User checkuser(String username, String password);

}
