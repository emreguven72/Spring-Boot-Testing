package com.spring.testing.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.testing.entity.Token;
import com.spring.testing.repository.TokenRepository;
import com.spring.testing.service.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
	private final TokenRepository tokenRepository;

	@Override
	public Optional<Token> findByToken(String token) {
		return tokenRepository.findByToken(token);
	}

	@Override
	public List<Token> findAllValidTokensByUser(int userId) {
		return tokenRepository.findAllValidTokensByUser(userId);
	}

	@Override
	public void save(Token token) {
		tokenRepository.save(token);
	}

}
