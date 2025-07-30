package com.coderscampus.assignment13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.assignment13.domain.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address,Long>{
	
	// I need to start with the register html and add
	// 1. a spot to add a address
	// 2. a spot to add an account 
	// then I need to add a save  and post function for both objects so that I can persist these objects
	// after that I need to edit the userId view so that I can see everything and be able to edit these objects
	//finally I need to be able to edit the accounts with a separate view and update the name that previously are just listed as numbers.

}
