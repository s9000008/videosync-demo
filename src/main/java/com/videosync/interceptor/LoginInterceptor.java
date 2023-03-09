package com.videosync.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.videosync.dto.UserDto;
@Component
public class LoginInterceptor implements HandlerInterceptor {

	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) {
		UserDto userRow = (UserDto)request.getSession().getAttribute("userRow");
		if(
				userRow == null || 
				userRow.getUid() == null || 
				userRow.getUsername() == null
				) {
			response.setStatus(302);
			return false;
		}
		return true;
	}
	
}
