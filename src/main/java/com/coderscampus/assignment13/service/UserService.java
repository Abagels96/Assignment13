package com.coderscampus.assignment13.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
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
	private AddressRepository addressRepo;
	@Autowired
	private AccountRepository accountRepo;

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

	public void addAccount(User user) {
		 
			Account newAccount = new Account();
			newAccount.getUsers().add(user);
			user.getAccounts().add(newAccount);
			accountRepo.save(newAccount);
			userRepo.save(user);

		

	}

	public Address addAddress(User user) {

		Address address = new Address();// address is null for some reason and it is irritating for sure
		address.setUser(user);

		user.setAddress(address);
		addressRepo.save(address);
        
		return address;
	}

	public void delete(Long userId) {
		userRepo.deleteById(userId);
	}

	

	public void update(Long userId, User newUser) {
		System.out.println("hello");
	User user = userRepo.findById(userId).orElseThrow();// add exception but this 
	// line selects the user in the database by Id and then I vainly try to edit it in the browser.

	user.setName(newUser.getName());
	user.setUsername(newUser.getUsername());
	user.setPassword(newUser.getPassword());
	
	 
	System.out.println(user);// this should print the user displayed in the browser
		Address userAddress= user.getAddress();
		Address newUserAddress= newUser.getAddress();
		
		
		if (userAddress== null) {
			Address address= new Address();
			address.setUser(user);// check if address is null and loads a new address needs to save it
			user.setAddress(address); // set new address to user object loaded from the form?
			System.out.println(address);
		}
		// always use the db object not the form object 
	userAddress.setAddressLine1(newUserAddress.getAddressLine1());
	userAddress.setAddressLine2(newUserAddress.getAddressLine2());
	userAddress.setRegion(newUserAddress.getRegion());
	userAddress.setCity(newUserAddress.getCity());
	userAddress.setCountry(newUserAddress.getCountry());
	userAddress.setZipCode(newUserAddress.getZipCode());
		 
		userRepo.save(user); // the full user object is saved
		

		
		
		}


		

	}


