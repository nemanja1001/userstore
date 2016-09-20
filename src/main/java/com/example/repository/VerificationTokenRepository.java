package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.User;
import com.example.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

	VerificationToken findByToken (String token);
	VerificationToken findbyUser(User user);
}
