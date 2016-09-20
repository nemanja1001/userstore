package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.dto.UserDto;


@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue
	@Column(nullable=false, unique=true)
	private Long id;
	
	@Column
	private String firstname;
	
	@Column
	private String lastname;
	
	@Column
	private String email;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private boolean enabled;
	
	public User () {
		super();
		this.enabled=false;
	}
	
	public User (UserDto user) {
		this.id = user.getId();
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.enabled=false;
	}
	
	public User(String firstname, String lastname, String email, String username, String password){
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled=false;
	}
	
	
	public void setId(Long id) {
		this.id=id;
	}
	
	public Long getId(){
		return this.id;
	}
	
	public void setFirstname(String name) {
		this.firstname=name;
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
	
	public void setEmail(String email) {
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
	
	public boolean getEnabled(){
		return this.enabled;
	}
	
	public void setEnabled(boolean en) {
		this.enabled = en;
	}
	
	public UserDto transformToDto(){
		UserDto dto = new UserDto();
		
		dto.setId(this.getId());
		dto.setFirstname(this.getFirstname());
		dto.setLastname(this.getLastname());
		dto.setEmail(this.getEmail());
		dto.setUsername(this.getUsername());
		dto.setPassword(this.getPassword());
		
		return dto;
		
	}
	
	public void updateFromDto(UserDto dto){
		this.setFirstname(dto.getFirstname());
		this.setLastname(dto.getLastname());
		this.setEmail(dto.getEmail());
		this.setUsername(dto.getUsername());
		this.setPassword(dto.getPassword());
	}
}
