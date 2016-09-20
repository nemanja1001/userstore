package com.example.registration;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.example.model.User;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent{

	private final String appUrl;
	private final Locale locale;
	private final User user;
	
	public OnRegistrationCompleteEvent(User user, Locale locale, String appUrl){
		super(user);
		
		this.user = user;
		this.locale = locale;
		this.appUrl = appUrl;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public Locale getLocale(){
		return this.locale;
	}
	
	public String getAppUrl(){
		return this.appUrl;
	}
}
