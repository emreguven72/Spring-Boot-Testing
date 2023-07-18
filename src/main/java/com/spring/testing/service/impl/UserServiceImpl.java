package com.spring.testing.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.testing.entity.Role;
import com.spring.testing.entity.User;
import com.spring.testing.model.CreateUserRequest;
import com.spring.testing.repository.UserRepository;
import com.spring.testing.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@Override
	public Optional<User> findById(int id) {
		User user = userRepository.findById(id).orElseThrow();
		return Optional.of(user);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow();
		return Optional.of(user);
	}

	@Override
	public Optional<User> save(CreateUserRequest request) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		User user = User.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.ADMIN)
				.build();

		return Optional.of(userRepository.save(user));
	}

}
