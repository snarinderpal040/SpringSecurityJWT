package com.learn.springboot.securityeazybank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.springboot.securityeazybank.entities.Customer;


public interface CustomerRepository extends JpaRepository<Customer, String> {
	
}
