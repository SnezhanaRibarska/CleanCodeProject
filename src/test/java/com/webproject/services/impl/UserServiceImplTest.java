package com.webproject.services.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.webproject.models.User;
import com.webproject.repositories.UserRepository;
import com.webproject.services.impl.UserServiceImpl;

public class UserServiceImplTest {

	private UserServiceImpl userService;
	private static final String USER_NAME = "snezhi";
	private static final String USER_PASSWORD = "123456";
	private List<User> usersFromRepository;
		
	@Mock
	private User testUser;
	@Mock
	private UserRepository userRepo;
	
	@BeforeEach
	public void setUp() {
	    MockitoAnnotations.initMocks(this);
	    usersFromRepository = new ArrayList<>();
	}
	
	@Test
	public void testValidateUser() {
		userService = new UserServiceImpl(userRepo);
		usersFromRepository = new ArrayList<>();
		usersFromRepository.add(testUser);
		Mockito.when(userRepo.findAll()).thenReturn(usersFromRepository);
		Mockito.when(testUser.getUserName()).thenReturn(USER_NAME);
		Mockito.when(testUser.getPassword()).thenReturn(USER_PASSWORD);
		User validatedUser = userService.validateUser(testUser);
		assertEquals(USER_NAME, validatedUser.getUserName(), "Expected user name is " + USER_NAME + " , but actually it is " + validatedUser.getUserName());
		assertEquals(USER_PASSWORD, validatedUser.getPassword(), "The password is wrong!");
	}
	
	@Test
	public void testValidateNewUserWithoutUsernameAndPassword() {
		userService = new UserServiceImpl(userRepo);
		User validatedUser = userService.validateNewUser(testUser);
		assertNull(validatedUser);
	}
	
	@Test
	public void testValidateNewUser() {
		userService = new UserServiceImpl(userRepo);
		Mockito.when(testUser.getUserName()).thenReturn(USER_NAME);
		Mockito.when(testUser.getPassword()).thenReturn(USER_PASSWORD);		
		User validatedUser = userService.validateNewUser(testUser);
		assertNotNull(validatedUser);
	}
	
}
