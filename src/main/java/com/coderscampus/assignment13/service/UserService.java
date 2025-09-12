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

	List<Account> accounts;

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

	public Account addAccount(Long userId) {
		
		User user = userRepo.findById(userId).orElseThrow();
		Account newAccount = new Account();

		newAccount.getUsers().add(user);
		user.getAccounts().add(newAccount);
		int accountNumber = user.getAccounts().size();

		
		accountRepo.saveAndFlush(newAccount);
    newAccount.setAccountName("Account # "+ accountNumber);
	
    System.out.println(newAccount);
		return accountRepo.save(newAccount);
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
		// line selects the user in the database by Id and then I vainly try to edit it
		// in the browser.

		user.setName(newUser.getName());
		user.setUsername(newUser.getUsername());
		user.setPassword(newUser.getPassword());

		Address userAddress = user.getAddress();
		Address newUserAddress = newUser.getAddress();

		if (userAddress == null) {
			Address address = new Address();
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
	 public Account selectAccount(Long accountId) {
		 return accountRepo.findById(accountId).orElseThrow();
	 }
 public Account renameAccount(Long accountId, String newName) {
	Account account= accountRepo.findById(accountId).orElseThrow();
	
account.setAccountName(newName);	
accountRepo.save(account);
	return account;
	 
	}

}
