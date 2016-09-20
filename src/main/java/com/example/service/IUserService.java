package com.example.service;

import com.example.dto.UserDto;
import com.example.model.User;
import com.example.model.VerificationToken;

public interface IUserService {

	User registerNewUserAccount(UserDto accountDto);
	User getUser(String verificationToken);
	void saveRegisteredUser(User user);
	void createVerificationToken(User user, String token);
	VerificationToken getVerificationToken(String verificationToken);
	
}
