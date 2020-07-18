package com.webproject.services;

import java.util.List;

import com.webproject.models.User;


public interface UserService {

	/**	
	* This method returns all existing users in DB
	* @return existing users
	*/
	List<User> findAll();
	
	/**	
	* This method validates the provided user
	* @param the user to be validated
	* @return the validated user or null if the user does not exist
	*/
	User validateUser(User user);
	
	/**	
	* This method registers the provided user
	* @param the user to be registered
	*/
	void register(User user);
	
	/**	
	* This method validates a new user
	* @param  the user to be validated
	* @return the validated user or null if the username or password are not provided	
	*/
	User validateNewUser(User user);
	
}
