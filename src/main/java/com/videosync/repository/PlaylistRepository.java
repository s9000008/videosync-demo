package com.videosync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.videosync.entity.video.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

	Playlist findByUid(String uid);
	
	@Query(nativeQuery = true, value="SELECT * FROM playlist WHERE creator = ?1 ")
	List<Playlist> getAllPlaylistByCreator(String creator);
	
	@Query(nativeQuery = true, value="SELECT * FROM playlist")
	List<Playlist> getAllPlaylist();
	
}
