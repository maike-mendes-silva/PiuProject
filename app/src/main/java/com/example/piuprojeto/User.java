package com.example.piuprojeto;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
	@Exclude
	String uid;

	String username;

	public User(){}

	public User(String uid, String username ){
		this.uid = uid;
		this.username = username;
	}

	public User(String username ){
		this.username = username;
	}

	public String getUid(){
		return uid;
	}

	public void setUid(String uid){
		this.uid = uid;
	}

	public String getUsername(){
		return username;
	}

	public void setUsername(String username){
		this.username = username;
	}

} 
