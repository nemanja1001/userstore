package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserDto {

	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private String username;
	private String password;
	
	public UserDto(){}
	
	public UserDto(String firstName, String lastName, String username, String password, String email){
		this.firstname=firstName;
		this.lastname=lastName;
		this.username=username;
		this.email = email;
		this.password=password;
	}
	
	public UserDto(UserDto dto) {
		this.firstname = dto.firstname;
		this.lastname = dto.lastname;
		this.username = dto.username;
		this.email = dto.email;
		this.password = dto.password;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
	
	public void setFirstname(String name) {
		this.firstname = name;
	}
	
	public String getFirstname(){
		return this.firstname;
	}
	
	public void setLastname(String last) {
		this.lastname = last;
	}
	
	public String getLastname(){
		return this.lastname;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setUsername(String user) {
		this.username = user;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public String getPassword(){
		return this.password;
	}
}
