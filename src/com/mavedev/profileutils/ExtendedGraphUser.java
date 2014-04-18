package com.mavedev.profileutils;

import com.facebook.model.GraphUser;

public class ExtendedGraphUser {

	GraphUser user;
	public static final String EMAIL = "email";
	public static final String LOCATION = "location";
	
	public ExtendedGraphUser(GraphUser user) {
		this.user = user;
	}
	
	public String getCity(){
		if(user.getLocation()!=null){
			return (String) user.getLocation().getProperty("name");
		}else{
			return null;
		}
	}
	
}
