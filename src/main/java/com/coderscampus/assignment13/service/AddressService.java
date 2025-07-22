package com.coderscampus.assignment13.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.repository.AddressRepository;
@Service
public class AddressService {
	
	AddressRepository addressRepo;
	
	public List< Address> findAllAddressesByUser(Long userId )
	{
		return addressRepo.findAll(); // do i need this?
	}
	
}
