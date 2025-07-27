package com.coderscampus.assignment13.service;

import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AddressRepository;

@Service
public class AddressService {

	AddressRepository addressRepo;

	public Address saveAddress(Address address) {
		return addressRepo.save(address);
	}

}
