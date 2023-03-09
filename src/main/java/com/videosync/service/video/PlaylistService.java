package com.videosync.service.video;

import java.util.List;

import com.videosync.entity.video.Playlist;

public interface PlaylistService {

	Playlist getPlaylist(long id)throws Exception;
	
	Playlist getPlaylistByUid(String uid)throws Exception;
	
	List<Playlist> getPlaylistByCreator(String creator)throws Exception;
	
	List<Playlist> getAllPlaylist();
	
	List<Playlist> getPlaylistByPage(int size, int pageSize);
	
}
