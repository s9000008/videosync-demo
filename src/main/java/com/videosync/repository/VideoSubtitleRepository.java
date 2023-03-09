package com.videosync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.videosync.entity.video.VideoSubtitle;

public interface VideoSubtitleRepository extends JpaRepository<VideoSubtitle, Long> {
	
	@Query(nativeQuery = true, value="SELECT * FROM video_subtitle where uid = ?1")
	VideoSubtitle getByUid(String uid);
	
	@Query(nativeQuery = true, value="SELECT * FROM video_subtitle where vid = ?1")
    List<VideoSubtitle> getAllByVid(long vid);
	
}
