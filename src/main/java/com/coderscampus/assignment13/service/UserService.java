package com.coderscampus.assignment13.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

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
@Transactional
public class UserService {
	private final UserRepository userRepo;
	private final AddressRepository addressRepo;
	private final AccountRepository accountRepo;

	public UserService(UserRepository userRepo, AddressRepository addressRepo, AccountRepository accountRepo) {
		this.userRepo = userRepo;
		this.addressRepo = addressRepo;
		this.accountRepo = accountRepo;
	}

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
		newAccount.setAccountName("Account # " + accountNumber);
		return accountRepo.save(newAccount);
	}

	public void addAddress(User user, Address address) {

		address.setUser(user);
		user.setAddress(address);

		addressRepo.save(address);
	}

	public void delete(Long userId) {
		System.out.println("I'm here as well");
		System.out.println(userId);
		userRepo.deleteById(userId);
	}

	public void update(Long userId, User newUser) {
		User user = userRepo.findById(userId).orElseThrow();
		user.setName(newUser.getName());
		user.setUsername(newUser.getUsername());
		user.setPassword(newUser.getPassword());
		Address userAddress = user.getAddress();
		Address newUserAddress = newUser.getAddress();

		if (userAddress == null) {
			userAddress = new Address();
			userAddress.setUser(user);
			user.setAddress(userAddress);

		}
		userAddress.setAddressLine1(newUserAddress.getAddressLine1());
		userAddress.setAddressLine2(newUserAddress.getAddressLine2());
		userAddress.setRegion(newUserAddress.getRegion());
		userAddress.setCity(newUserAddress.getCity());
		userAddress.setCountry(newUserAddress.getCountry());
		userAddress.setZipCode(newUserAddress.getZipCode());
		userRepo.save(user);

	}

	public Account selectAccount(Long accountId) {
		return accountRepo.findById(accountId).orElseThrow();
	}

	public Account renameAccount(Long accountId, String newName) {
		Account account = accountRepo.findById(accountId).orElseThrow();
		account.setAccountName(newName);
		accountRepo.save(account);
		return account;

	}

}
