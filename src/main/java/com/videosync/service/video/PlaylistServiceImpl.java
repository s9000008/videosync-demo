package com.videosync.service.video;

import java.util.List;


import com.videosync.entity.video.Playlist;
import com.videosync.repository.PlaylistRepository;
import com.videosync.service.A_Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PlaylistServiceImpl extends A_Service<Playlist> implements PlaylistService{

	@Autowired
	PlaylistRepository repo;

	@Override
	public Playlist getPlaylist(long id) throws Exception {
		Optional<Playlist> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Playlist getPlaylistByUid(String uid) throws Exception {
		return repo.findByUid(uid);
	}

	@Override
	public List<Playlist> getPlaylistByCreator(String creator) throws Exception {
		return repo.getAllPlaylistByCreator(creator);
	}

	@Override
	public List<Playlist> getAllPlaylist() {
		return repo.getAllPlaylist();
	}

	@Override
	public List<Playlist> getPlaylistByPage(int size, int pageSize) {
		return repo.getAllPlaylist();
	}

}
