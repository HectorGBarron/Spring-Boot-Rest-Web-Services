package com.training.fisrt.app.ws.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.training.fisrt.app.ws.exceptions.UserServiceException;
import com.training.fisrt.app.ws.io.entity.UserEntity;
import com.training.fisrt.app.ws.io.repositories.UserRepository;
import com.training.fisrt.app.ws.service.UserService;
import com.training.fisrt.app.ws.shared.Utils;
import com.training.fisrt.app.ws.shared.dto.UserDto;
import com.training.fisrt.app.ws.ui.model.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto user) {

		if (userRepository.findByEmail(user.getEmail()) != null)
			//throw new RuntimeException("Record already exists");
			throw new UserServiceException(user.getEmail()+" "+ ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
		UserEntity userEntity = new UserEntity();

		BeanUtils.copyProperties(user, userEntity);

		String publicUserId = utils.generateUserId(30);
		userEntity.setUserId(publicUserId);

		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		UserEntity storedUserDetails = userRepository.save(userEntity);

		UserDto returnValue = new UserDto();

		BeanUtils.copyProperties(storedUserDetails, returnValue);

		return returnValue;
	}

	@Override

	public UserDto getUser(String email) {

		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		UserDto returnValue = new UserDto();

		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

@Override
	public UserDto getUserById(String userId) {
		UserDto returnValue= new UserDto();

		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UsernameNotFoundException(userId);

		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

@Override
public UserDto updateUser(String userId, UserDto userDto) {

	UserDto returnValue= new UserDto();

	UserEntity userEntity = userRepository.findByUserId(userId);

	if (userEntity == null)
		throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
	
	if (userDto.getFirstName()!=null)
		userEntity.setFirstName(userDto.getFirstName());
	
	if (userDto.getLastName()!=null)
		userEntity.setLastName(userDto.getLastName());
	
	
	UserEntity updatedUserDetails =userRepository.save(userEntity);
	BeanUtils.copyProperties(updatedUserDetails, returnValue);
	
	
	return  returnValue;
}






}
