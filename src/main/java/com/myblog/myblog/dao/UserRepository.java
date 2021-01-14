package com.myblog.myblog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblog.myblog.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsernameAndPassword(String username, String password);

}
