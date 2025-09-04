package com.coderscampus.assignment13.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.Account;
//import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;


	@GetMapping("/register")
	public String getCreateUser(ModelMap model) {
		User user = new User();
		List<Account> accounts = new ArrayList<>();
		model.put("accounts", accounts);
		model.put("user", user);
		return "register";
	}

	@PostMapping("/register")
	public String postCreateUser(User user) {
		System.out.println(user);
		if (user.getAddress() != null) {
			userService.addAddress(user);
		}
		return "redirect:/register";
	}

	@GetMapping("/users")
	public String getAllUsers(ModelMap model) {
		Set<User> users = userService.findAll();

		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
		}

		return "users";
	}

	@GetMapping("/users/{userId}")
	public String getOneUser(ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		List<Account> accounts = user.getAccounts();
		Address newAddress = user.getAddress();
		model.put("user", user);
		model.put("accounts", accounts);
		model.put("address", newAddress);
		return "userId";
	}

	@PostMapping("/users/{userId}") // probably the culprit to this problem
	public String postOneUser(@PathVariable Long userId, @ModelAttribute User user) {
		System.out.println(user);
		userService.update(userId, user);
		return "redirect:/users";
	}

	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser(@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}

	@PostMapping("/users/{userId}/accounts")
	public String addAccount( @PathVariable Long userId) {
		
		System.out.println("booyah");
	userService.addAccount(userId);
		return "redirect:/users/{userId}";
	}

}
