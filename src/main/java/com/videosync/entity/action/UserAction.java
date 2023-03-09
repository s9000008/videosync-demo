package com.videosync.entity.action;
import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class UserAction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
