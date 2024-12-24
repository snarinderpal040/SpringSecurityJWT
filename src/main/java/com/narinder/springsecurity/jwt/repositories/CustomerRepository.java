package com.narinder.springsecurity.jwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.narinder.springsecurity.jwt.entities.Customer;


public interface CustomerRepository extends JpaRepository<Customer, String> {
	
}
