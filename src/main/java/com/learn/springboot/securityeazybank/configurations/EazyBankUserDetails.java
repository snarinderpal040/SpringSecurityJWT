package com.learn.springboot.securityeazybank.configurations;

import com.learn.springboot.securityeazybank.entities.Customer;
import com.learn.springboot.securityeazybank.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class EazyBankUserDetails implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Request coming to custom load by username");
        List<GrantedAuthority> authorities;
        Customer customer;
        boolean present = repository.findById(username).isPresent();
        if(present){
            customer = repository.findById(username).get();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(customer.getRole()));
        }else{
            throw new UsernameNotFoundException("Customer not found with username : " + username);
        }
        return new User(customer.getUsername(), customer.getPassword(), authorities);
    }
    
}
