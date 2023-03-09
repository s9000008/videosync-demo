package com.videosync.controller.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

//import com.videosync.controller.video.ChannelActionController;
//import jdk.tools.jlink.internal.plugins.ExcludePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.SessionStatus;

import com.videosync.Constant;
import com.videosync.dto.UserDto;
import com.videosync.entity.user.User;
import com.videosync.service.user.UserServiceImpl;
@RequestMapping("api/user")
@RestController
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
    private UserServiceImpl userService;


    /**
     * 註冊帳號
     */
	@RequestMapping(value = "/register", headers="Content-Type=application/json", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> register(
    		@RequestBody Map<String, Object> payload) {
    	String username = (String)payload.getOrDefault("username", null);
    	String password = (String)payload.getOrDefault("password", null);
    	Map<String, Object> resultJson = new HashMap<String, Object>(2);
    	String message = "";
    	Boolean success = false;
        try {
        	if(username == null || password == null) {
        		message = Constant.PARAM_EMPTY;
        	}else if (userService.findByUsername(username).isEmpty()) {
        		User user = new User(username, password);
                userService.save(user);
                success = true;
        	}else {
        		message = Constant.PARAM_EMPTY + Constant.DATA_EXISTS;
        	}
        } catch (Exception e) {
			message = Constant.ERROR;
			LOGGER.error(e.getMessage(), e);
        } finally {
        	
        }
        resultJson.put("message", message);
    	resultJson.put("success", success);
        return resultJson;
    }
	@RequestMapping(value = "/login", headers="Content-Type=application/json", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> login(
    		HttpSession session, 
    		@RequestBody Map<String, Object> payload
    		) {
		String username = (String)payload.getOrDefault("username", null);
    	String password = (String)payload.getOrDefault("password", null);
    	Map<String, Object> resultJson = new HashMap<String, Object>(2);
    	String message = Constant.LOGIN_SUCCESS;
    	Boolean success = false;
		try {
			User user = userService.findByUsernameAndPassword(username, password);
			List<User> list = new ArrayList<User>();
			if (user == null) {
				message = Constant.LOGIN_FAIL;
			} else {
				list = userService.login(user.getUsername(), user.getPassword());
				if (list != null && list.size() == 1) {
					//登入成功後設置session的值
					UserDto userRow = new UserDto(list.get(0));
					userRow.setLoginDate(new Date());
					session.setAttribute("userRow", userRow);
					session.setMaxInactiveInterval(7200);
					success = true;
				} else {
					message = Constant.LOGIN_FAIL;
				}
			}
		}catch(Exception e){
			message = Constant.ERROR;
			LOGGER.error(e.getMessage(), e);
		}
        resultJson.put("message", message);
    	resultJson.put("success", success);
        return resultJson;
    }
	/**
	 * 登出
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/singOut", method = RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> signout( HttpSession session, SessionStatus sessionStatus){
    	Map<String, Object> resultJson = new HashMap<String, Object>(2);
    	String message = "";
    	Boolean success = false;
		try {
			//清除Session
			session.removeAttribute("userRow");
			sessionStatus.setComplete();
			success = true;
		}catch(Exception e) {
			message = Constant.ERROR;
			LOGGER.error(e.getMessage(), e);
		}
        resultJson.put("message", message);
    	resultJson.put("success", success.toString());
        return resultJson;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Map<String, Object> userinfo(@SessionAttribute("userRow") UserDto userRow){
    	Map<String, Object> resultJson = new HashMap<String, Object>(5);
    	Boolean success = false;
    	String message = "";
    	if(userRow != null) {
	    	try {
		    	resultJson.put("username", userRow.getUsername());
		    	resultJson.put("uid", userRow.getUid());
		    	resultJson.put("loginDate", String.valueOf(userRow.getLoginDate().getTime()));
		    	success = true;
	    	}catch(Exception e) {
				message = Constant.ERROR;
				LOGGER.error(e.getMessage(), e);
	    	}
    	}
    	resultJson.put("success", success);
    	resultJson.put("message", message);
        return resultJson;
    }
    
}
