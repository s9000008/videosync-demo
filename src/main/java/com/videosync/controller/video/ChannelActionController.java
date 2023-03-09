package com.videosync.controller.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.videosync.Constant;
import com.videosync.entity.action.ChannelAction;
import com.videosync.entity.action.UserAction;
import com.videosync.utils.DataBucketUtil;


@Controller
public class ChannelActionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelActionController.class);
	
	@MessageMapping("/channelControl")
	@SendTo("/room/getResponse/")
	public ChannelAction action(
			UserAction userAction) throws InterruptedException{
		
		ChannelAction responseAction = new ChannelAction();
		try {
			responseAction.setId(userAction.getId());
			responseAction.setAction(userAction.getAction());
			responseAction.setTimestamp(userAction.getTimestamp());
			responseAction.setUserUid(userAction.getUserUid());
			responseAction.setVideoUid(userAction.getVideoUid());
		}catch(Exception e) {
			LOGGER.error(Constant.ERROR,e);
		}
		return responseAction;
	}
}
