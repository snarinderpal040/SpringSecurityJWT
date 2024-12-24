package com.narinder.springsecurity.jwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.narinder.springsecurity.jwt.dtos.AuthenticateResponse;
import com.narinder.springsecurity.jwt.dtos.LoginRequest;
import com.narinder.springsecurity.jwt.dtos.RegisterRequest;
import com.narinder.springsecurity.jwt.services.LoginService;

@RestController
public class LoginController {

	@Autowired
	LoginService service;
	
	@PostMapping(path = "/register")
	public ResponseEntity<AuthenticateResponse> registerUser(@RequestBody RegisterRequest request) {
		return new ResponseEntity<AuthenticateResponse>(service.register(request), HttpStatus.OK);
	}
	
	@PostMapping(path = "login")
	public ResponseEntity<AuthenticateResponse> login(@RequestBody LoginRequest request){
		return new ResponseEntity<>(service.login(request), HttpStatus.OK);
	}
}
