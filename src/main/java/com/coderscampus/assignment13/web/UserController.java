package com.coderscampus.assignment13.web;

import java.util.Arrays;
import java.util.Set;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AddressService;
import com.coderscampus.assignment13.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private AddressService addressService;
	
	
	
	@GetMapping("/register")
	
	public String getCreateUser (ModelMap model) {
		
		model.put("user", new User());
		model.put("address", new Address());
//		model.put("account", user.getAccounts().get(0).toString());
		
		return "register";
	}
	
	@PostMapping("/register")
	public String postCreateUser (User user) {
		
		System.out.println(user);
		
		
		// addAccountToUser- rename at some point
		if( user.getUserId()==null) {
		userService.addAddress(user);
	 addressService.saveAddress(user.getAddress()); 
		userService.saveUser(user);
		}
//		userService.addAccount(user, user.getAccounts().add(0, null));
		return "redirect:/register";
	}
	
	@GetMapping("/users")
	public String getAllUsers (ModelMap model) {
		Set<User> users = userService.findAll();
		
		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
		}
		
		return "users";
	}
	
	@GetMapping("/users/{userId}")
	public String getOneUser (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
	 		model.put("users", Arrays.asList(user));
		model.put("user", user);
		return "userId";
	}
	
	@PostMapping("/users/{userId}")
	public String postOneUser (User user) {
		userService.saveUser(user);
		
		return "redirect:/users/"+user.getUserId();
	}
	
	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser (@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}
}
