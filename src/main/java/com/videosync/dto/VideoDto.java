package com.videosync.dto;

import org.json.JSONObject;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VideoDto {
	
	private long id;
	
	private String uid;
	
	private String name;
	
	private String path;
	
	private String subtitlePath;
	
	public VideoDto(long id, String path, String uid) {
		this.id = id;
	    this.path = path;
	    this.uid = uid;
	}
	
	public VideoDto(com.videosync.entity.video.Video video) {
		this.id = video.getId();
		this.name = video.getName();
	    this.path = video.getPath();
	    this.uid = video.getUid();
	}
	
	
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("path", this.path);
		json.put("name", this.name);
		json.put("uid", this.uid);
		json.put("subtitlePath", this.subtitlePath);
		return json;
	}
	
}
