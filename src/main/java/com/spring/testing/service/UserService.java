package com.spring.testing.service;

import java.util.List;
import java.util.Optional;

import com.spring.testing.entity.User;
import com.spring.testing.model.CreateUserRequest;

public interface UserService {
	
	List<User> findAll();
	
	Optional<User> findById(int id);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> save(CreateUserRequest request);
	
}
