package com.coderscampus.assignment13.service;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.AddressRepository;
import com.coderscampus.assignment13.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private AddressRepository addressRepo;

	public Set<User> findAll() {
		return userRepo.findAllUsersWithAccountsAndAddresses();
	}

	public User findById(Long userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		return userOpt.orElse(new User());
	}

	public User saveUser(User user) {
System.out.println(user);

		return userRepo.save(user);
		
		
	}

	public void delete(Long userId) {
		userRepo.deleteById(userId);
	}

	public void addAccount(User user, List<Account> accounts) {
		user.setAccounts(accounts);
		userRepo.save(user);
	}

	public void update(Long userId, User user) {
		// add null checks to this 
		userRepo.findById(userId);
		userRepo.setUserInfoById(null, null, null, null);
		// I need to make a method to update data right here
		userRepo.save(user);
		// TODO Auto-generated method stub
		
	}

}
