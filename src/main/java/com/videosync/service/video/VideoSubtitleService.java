package com.videosync.service.video;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.videosync.entity.video.VideoSubtitle;

public interface VideoSubtitleService {
	
	VideoSubtitle getByUid(String uid);
	
	List<VideoSubtitle> getAllByVid(long vid);
	
	void saveSubtitle(MultipartFile file, long vid, String uid, String userUid) throws Exception ;
	
}
