package com.webproject.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.webproject.models.User;
import com.webproject.services.UserService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {

	private UserService userService;

	@Autowired
	public RegisterController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("register");
		mav.addObject("user", new User());
		return mav;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("user") User user) {
		ModelAndView mav;
		User u = userService.validateNewUser(user);
		if (null != u) {
			userService.register(user);
			request.getSession().setAttribute("userID", u.getId());
			mav = new ModelAndView("redirect:/home");
		} else {
			mav = new ModelAndView("register");
			mav.addObject("message", "Username and password cann`t be null!");
		}
		return mav;
	}

}
