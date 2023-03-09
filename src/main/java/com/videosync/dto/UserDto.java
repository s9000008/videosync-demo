package com.videosync.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
	
	private String uid;
	
	private String username;
	
	private Date loginDate;
	
	public UserDto(String uid, String username) {
	    this.uid = uid;
	    this.username = username;
	    this.loginDate = new Date();
	}
	
	public UserDto(com.videosync.entity.user.User user) {
		this.uid = user.getUid();
	    this.username = user.getUsername();
	    this.loginDate = new Date();
	}
}
