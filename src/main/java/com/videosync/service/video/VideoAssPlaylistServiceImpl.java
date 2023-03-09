package com.videosync.service.video;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videosync.entity.video.VideoAssPlaylist;
import com.videosync.repository.VideoAssPlaylistRepository;
import com.videosync.service.A_Service;

@Service
public class VideoAssPlaylistServiceImpl extends A_Service<VideoAssPlaylist> implements VideoAssPlaylistService{
	
	@Autowired
	VideoAssPlaylistRepository repo;
	
	@Override
	public List<VideoAssPlaylist> getByPid(long pid) throws Exception {
		return repo.getVideosByPid(pid);
	}
}
