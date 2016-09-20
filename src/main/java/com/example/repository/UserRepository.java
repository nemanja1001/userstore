package com.example.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.model.User;

public interface UserRepository extends Repository<User, Long>{

	List<User> findAll();
	
	User findOne(Long id);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User save(User user);
	
	void delete(User user);
	
}