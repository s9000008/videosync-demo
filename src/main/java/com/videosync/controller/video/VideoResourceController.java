package com.videosync.controller.video;

import java.io.InputStream;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videosync.Constant;
import com.videosync.dto.VideoDto;
import com.videosync.entity.video.Video;
import com.videosync.entity.video.VideoSubtitle;
import com.videosync.service.StreamingService;
import com.videosync.service.video.VideoServiceImpl;
import com.videosync.service.video.VideoSubtitleServiceImpl;
import reactor.core.publisher.Mono;

/**
 * 串流
 * @author Tenghung
 *
 */
@RestController
@RequestMapping("api/video/resource")
public class VideoResourceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(VideoResourceController.class);

	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private StreamingService service;

	@Autowired
	private VideoServiceImpl videoService;
	
	@Autowired
	private VideoSubtitleServiceImpl videoSubtitleService;
	
	/**
	 * 取得串流資源位址
	 * @param uid
	 * @return
	 */
	@GetMapping(value = "/watch/{uid}")
	public ResponseEntity<String> getVideoOnGCS(@PathVariable String uid) {
		JSONObject resultJson = new JSONObject();
		boolean success = false;
    	String message = Constant.SAVE_SUCCESS;
		Video video = null;
		try {
			video = videoService.getVideo(uid);
			VideoDto videoRow = new VideoDto(video);
			resultJson.put("item", videoRow.toJson());
			success = true;
		} catch (Exception e) {
			message = Constant.ERROR;
			LOGGER.error(e.getMessage(), e);
		}
		resultJson.put("success", success);
    	resultJson.put("message", message);
    	
        return ResponseEntity.ok(resultJson.toString());
	}
	
	
	/**
	 * 串流 棄用
	 * @param uid
	 * @param range
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value = "/watch/old/{uid}",produces = "video/mp4")
	public Mono<Resource> getVideos(@PathVariable String uid, @RequestHeader("Range") String range) throws Exception {
		throw new Exception("NOT SERVICE");
		/*Mono<Resource> resource = null;
		Video video = null;
		try {
			System.out.println("Range: " + range);
			video = videoService.getVideo(uid);
		} catch (Exception e) {
			System.out.println("test");
			e.printStackTrace();
		}
		
		if(video != null) {
			String filename = String.format("%s.%s", video.getUid());
			System.out.println("filename:" + filename);
			resource = service.getVideo(filename);
			System.out.println(resource);
		}else {
			System.out.println("TEST resource is null");
		}
		return null;*/
	}
	
}
