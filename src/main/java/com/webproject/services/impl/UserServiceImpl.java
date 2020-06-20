package com.webproject.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webproject.models.User;
import com.webproject.repositories.UserRepository;
import com.webproject.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User validateUser(User u) {
		List<User> users = findAll();
		for (User user : users) {
			if (user.getUserName().equalsIgnoreCase(u.getUserName()) && user.getPassword().equals(u.getPassword())) {
				return user;
			}
		}
		return null;
	}

	public void register(User user) {
		userRepository.save(user);
	}

	public User validateNewUser(User user) {
		if (user.getUserName() == null || user.getPassword() == null) {
			return null;
		}
		return user;
	}

}
