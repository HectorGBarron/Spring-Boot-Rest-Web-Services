package com.training.fisrt.app.ws.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.training.fisrt.app.ws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UserDetailsService;
public interface UserService extends UserDetailsService{

	UserDto createUser(UserDto user);
	
	UserDto getUser(String email);
	
	UserDto getUserById(String userId);

	UserDto updateUser(String userId, UserDto userDto);
	
}
