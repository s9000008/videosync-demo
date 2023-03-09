package com.videosync.controller.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebSocketController {
	
	@RequestMapping("/websocket/{name}")

	public String webSocket(@PathVariable String name,Model model){
	    try{
	        System.out.println("跳转到websocket的页面上");
	        model.addAttribute("username",name);
	        return "websocket";
	    }
	    catch (Exception e){
	    	System.out.println("跳转到websocket的页面上发生异常，异常信息是："+e.getMessage());
	        return "error";
	    }
	}

}
