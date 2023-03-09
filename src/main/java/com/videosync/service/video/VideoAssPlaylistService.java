package com.videosync.service.video;

import java.util.List;

import com.videosync.entity.video.VideoAssPlaylist;

public interface VideoAssPlaylistService {
	
	//List<VideoAssVideoList> getByVideoListId(String videoListId)throws Exception;
	
	List<VideoAssPlaylist> getByPid(long pid)throws Exception;
	
}
