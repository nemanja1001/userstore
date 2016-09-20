package com.example.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.dto.UserDto;
import com.example.model.User;
import com.example.model.VerificationToken;
import com.example.registration.OnRegistrationCompleteEvent;
import com.example.service.UserService;

@RestController
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
    @Autowired
    private MessageSource messages;

	
	@RequestMapping(value = "/user/registration", method = RequestMethod.POST)
	public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto accountDto, BindingResult result, WebRequest request, Errors errors){
		if (result.hasErrors()){
			return new ModelAndView("registration", "user", accountDto);
		}
		
		User registered = new User(accountDto);
		if (registered == null){
			result.rejectValue("email", "message.regError");
		}
		
		try {
			String appUrl = request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
		}catch (Exception e){
			return new ModelAndView("emailError", "user", accountDto);
		}
		return new ModelAndView("successRegister", "user", accountDto);
	}
	
    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token){
        Locale locale = request.getLocale();
        
    	VerificationToken verificationToken = userService.getVerificationToken(token);
    	if (verificationToken == null) {
    		String message = messages.getMessage("auth.message.invalidToken", null, locale);
    		model.addAttribute("message", message);
    		return "redirect:/badUser.html?lang=" + locale.getLanguage();
    	}
    	
    	User user = verificationToken.getUser();
    	Calendar cal = Calendar.getInstance();
    	if ((verificationToken.getExpiryDate().getTime()-cal.getTime().getTime()) <=0) {
    		String messageValue = messages.getMessage("auth.message.expired", null, locale);
    		return "redirect:/badUser.html?lang=" + locale.getLanguage();
    	}
    	
    	user.setEnabled(true);
    	userService.saveRegisteredUser(user);
    	return "redirect:/login.html?lang=" + request.getLocale().getLanguage();
    }
    
    
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
		System.out.println("[ "+ userDto + " inside User Controller ]");
		UserDto newUser = userService.createUser(userDto); 
		return new ResponseEntity<>(newUser.getId(), HttpStatus.CREATED);
	
	}
	
	@RequestMapping(value="/users", method = RequestMethod.GET)
	public ResponseEntity<List<UserDto>> getUsers() {
		List<UserDto> responseDtos = userService.findAll();
		return new ResponseEntity<List<UserDto>>(responseDtos, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		UserDto response = userService.getUser(id);
		return new ResponseEntity<UserDto>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser (@PathVariable Long id){
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser (@PathVariable Long id, @RequestBody UserDto dto){
		dto.setId(id);
		userService.updateUser(dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
