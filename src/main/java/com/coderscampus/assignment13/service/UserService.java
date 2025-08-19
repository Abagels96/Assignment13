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
		
			user.setAddress(address);
			addressRepo.save(address);

	
		return address;
	}
	public void delete(Long userId) {
		userRepo.deleteById(userId);
	}

	public void addAccount(User user, List<Account> accounts) {
		user.setAccounts(accounts);
		userRepo.save(user);
	}

	public void update(Long userId, String name,String username,String password,String address1,String address2,String region,
		String country,String city, String zipCode) {
		User user= userRepo.findById(userId).orElseThrow();// add null checks to this 
		user.setName(name);
		user.setUsername(username);
		user.setPassword(password);
		
	 Address address= user.getAddress();
	 if(user.getAddress()==null) {
			 address= new Address();
			user.setAddress(address);
		}
	
	// this might be the cause of the problem. 
	address.setAddressLine1(address1);
	address.setAddressLine2(address2);
	address.setRegion(region);
	address.setCity(city);
	address.setCountry(country);
	address.setCity(city);
	address.setZipCode(zipCode);
		// I need to make a method to update data right here
		userRepo.save(user);
		addressRepo.save(address);
		// TODO Auto-generated method stub
		
	}

}
