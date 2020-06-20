package com.webproject.services;

import java.util.List;

import com.webproject.models.User;

public interface UserService {

	List<User> findAll();
	
	User validateUser(User user);
	
	void register(User user);
	
	User validateNewUser(User user);
	
}
