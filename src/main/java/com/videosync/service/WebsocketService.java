package com.videosync.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.videosync.entity.action.ChannelAction;

public class WebsocketService {
	@Autowired
    private SimpMessagingTemplate template;

    /**
     * 广播
     * 发给所有在线用户
     *
     * @param msg
     */
    public void sendMsg(ChannelAction action) {
        //template.convertAndSend(Constant.PRODUCERPATH, msg);
    }

    /**
     * 发送给指定用户
     * @param users
     * @param msg
     */
    public void send2Users(List<String> uid, ChannelAction action) {
    	uid.forEach(item -> {
            template.convertAndSendToUser(item, "", action);
        });
    }
}
