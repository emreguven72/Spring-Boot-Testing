package com.spring.testing.service;

import java.util.List;
import java.util.Optional;

import com.spring.testing.entity.Token;

public interface TokenService {
	
	Optional<Token> findByToken(String token);
	
	List<Token> findAllValidTokensByUser(int userId);
	
	void save(Token token);
	
}
