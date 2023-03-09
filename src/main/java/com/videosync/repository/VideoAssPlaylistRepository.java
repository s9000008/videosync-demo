package com.videosync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.videosync.entity.video.VideoAssPlaylist;

public interface VideoAssPlaylistRepository extends JpaRepository<VideoAssPlaylist, Long> {
	
	/*@Query(nativeQuery = true, 
			value="SELECT id, name, uid, creator FROM video_ass_video_list list WHERE video_list_id = ?1 ")
	List<VideoAssVideoList> getVideosByLid(String lid);*/
	
	@Query(nativeQuery = true, 
			value="SELECT id, vid, pid, sort FROM video_ass_playlist list WHERE pid = ?1 order by sort")
	List<VideoAssPlaylist> getVideosByPid(long pid);
	
	@Query(nativeQuery = true, 
			value="SELECT MAX(sort)  FROM video_ass_playlist list WHERE pid = ?1 ")
	Integer getLastSort(long pid);
	
	
}
