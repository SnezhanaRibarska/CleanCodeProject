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
		userService = new UserServiceImpl(userRepo);
	}
	
	@Test
	public void testValidateUser() {
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
		User validatedUser = userService.validateNewUser(testUser);
		assertNull(validatedUser);
	}
	
	@Test
	public void testValidateNewUser() {
		Mockito.when(testUser.getUserName()).thenReturn(USER_NAME);
		Mockito.when(testUser.getPassword()).thenReturn(USER_PASSWORD);		
		User validatedUser = userService.validateNewUser(testUser);
		assertNotNull(validatedUser);
	}
	
	@Test
	public void testRegisterUser() {
		Mockito.when(userRepo.save(testUser)).thenReturn(testUser);
		userService.register(testUser);
		Mockito.verify(userRepo).save(testUser);
	}
	
}
