package com.coderscampus.assignment13.service;

import java.time.LocalDate;
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

		return userRepo.save(user);
	}

	public void addAccount(User user, String nameOfAccount) {
		if (user.getUserId() == null) {
			Account placeholder = new Account();
			placeholder.setAccountName(nameOfAccount);
			placeholder.getUsers().add(user);// I got to find out why Trevor did getusers.add instead of setUsers
			// fix name of placeholder so Kevin is happy
			user.getAccounts().add(placeholder);
			accountRepo.save(placeholder);

		}

	}

	public Address addAddress(User user) {

		Address address = new Address();// address is null for some reason and it is irritating for sure
		address.setUser(user);
		if (user.getUserId() == null) {
			user.setAddress(address);
			addressRepo.save(address);

		}
		return address;

	}

	public void delete(Long userId) {
		userRepo.deleteById(userId);
	}
}
