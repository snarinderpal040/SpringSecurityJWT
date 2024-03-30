package com.learn.springboot.securityeazybank.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learn.springboot.securityeazybank.configurations.EazyBankUserDetails;
import com.learn.springboot.securityeazybank.dtos.AuthenticateResponse;
import com.learn.springboot.securityeazybank.dtos.LoginRequest;
import com.learn.springboot.securityeazybank.dtos.RegisterRequest;
import com.learn.springboot.securityeazybank.entities.Customer;
import com.learn.springboot.securityeazybank.jwt.JwtService;
import com.learn.springboot.securityeazybank.repositories.CustomerRepository;


@Service
public class LoginService {
	
	@Autowired
    CustomerRepository repository;
	
	@Autowired
	EazyBankUserDetails userDetails;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
	AuthenticationManager authenticationManager;
    
    @Autowired
    JwtService jwtService;

	public AuthenticateResponse register(RegisterRequest request) {
		Customer customer = Customer.builder()
				.enabled(true)
				.password(passwordEncoder.encode(request.getPassword()))
				.username(request.getUsername())
				.role("USER")
				.build();
		
        Customer save = repository.save(customer);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(save.getRole()));
        UserDetails userDetails = new User(save.getUsername(), save.getPassword(), authorities);
        String token = jwtService.generateToken(userDetails);
		return AuthenticateResponse.builder()
				.token(token)
				.build();
	}

	public AuthenticateResponse login(LoginRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		UserDetails userByUsername = userDetails.loadUserByUsername(request.getUsername());
		String token = jwtService.generateToken(userByUsername);
		return AuthenticateResponse.builder()
				.token(token)
				.build();
	}

}
