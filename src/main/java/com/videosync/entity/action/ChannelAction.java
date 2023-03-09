package com.videosync.entity.action;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ChannelAction {
	
	
	
	private String id;
	/**
	 * 0 = pause
	 * 1 = play
	 * 2 = goto
	 */
	private int action;
	private int timestamp;
	private String userUid;
	private String videoUid;
}
