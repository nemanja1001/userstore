package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.UserDto;
import com.example.model.User;
import com.example.model.VerificationToken;
import com.example.repository.UserRepository;
import com.example.repository.VerificationTokenRepository;

@Service
@Transactional
public class UserService implements IUserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	VerificationTokenRepository tokenRepository;
	
	
	@Override
    public User registerNewUserAccount(UserDto accountDto) {
         
        User user = new User();
        user.setFirstname(accountDto.getFirstname());
        user.setLastname(accountDto.getLastname());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());
        return userRepository.save(user);
    }
 
    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
     
    @Override
    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }
     
    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
     
    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }
     
    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

	
	public UserDto createUser(UserDto userToBeCreated){
		System.out.println("[ " + userToBeCreated + " inside User Service ]");
		System.out.println("Store in repository");
		User newUser = userRepository.save(new User(userToBeCreated));
		
		return newUser.transformToDto();
	}
	
	public void updateUser(UserDto userToBeUpdated){
		
		User user = userRepository.findOne(userToBeUpdated.getId());
		
		if (user!=null){
			user.updateFromDto(userToBeUpdated);
			userRepository.save(user);
		}
		
	}
	
	public UserDto getUser(Long id){
		
		UserDto responseDto = null;
		
		User user = userRepository.findOne(id);
		
		if (user!=null){
			responseDto = user.transformToDto();
		}
		
		return responseDto;
		
	}
	
	public List<UserDto> findAll(){
		
		List<User> users = userRepository.findAll();
		
		List<UserDto> responseDtos = new ArrayList<UserDto>();
		
		for (User user : users){
			responseDtos.add(user.transformToDto());
		}
		
		return responseDtos;
	}
	
	public void deleteUser(Long id){
		User user = userRepository.findOne(id);
		
		if (user!=null) {
			userRepository.delete(user);
		}
	}
}
