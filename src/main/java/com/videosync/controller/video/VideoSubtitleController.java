package com.videosync.controller.video;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.videosync.Constant;
import com.videosync.dto.UserDto;
import com.videosync.dto.VideoDto;
import com.videosync.entity.video.Video;
import com.videosync.entity.video.VideoSubtitle;
import com.videosync.service.video.VideoSubtitleServiceImpl;
import com.videosync.utils.DataBucketUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/subtitle")
@AllArgsConstructor
public class VideoSubtitleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(VideoSubtitleController.class);

	@Autowired
    private VideoSubtitleServiceImpl service;
	
	@Autowired
	private DataBucketUtil dataBucketUtil;
	
	/**
	 * 新增
	 * @param file
	 * @param name
	 * @param user
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<String> save(
    		@RequestParam("file") MultipartFile file, 
    		@RequestParam("name") String name,
    		@RequestParam("vid") String vid,
    		@SessionAttribute("userRow") UserDto user
    		) throws Exception {
    	JSONObject resultJson = new JSONObject();
    	boolean success = false;
    	String message = Constant.SAVE_SUCCESS;
    	if(user == null) {
    		message = Constant.NON_LOGIN;
    	}else {
    		if(vid.isEmpty()) {
    			message = Constant.PARAM_EMPTY;
			}else if(file.isEmpty()) {
	    		message = Constant.PARAM_EMPTY;
	    	}else {
	    		String filename = file.getOriginalFilename();
	    		message = Constant.EXTENSION_NOT_SUPPORT;
	    		try {
	    			long videoId = 0;
	    			videoId = Long.parseLong(vid);
	    			service.saveSubtitle(file, videoId, user.getUid(), user.getUid());
	    			message = Constant.SAVE_SUCCESS;
	    			success = true;
	    		}catch(Exception e) {
					LOGGER.error(e.getMessage(), e);
	    			message = Constant.ERROR;
	    		}
	    		if(filename.endsWith(".mp4") || filename.endsWith(".mkv")) {}
	    	}
    	}
    	resultJson.put("success", success);
    	resultJson.put("message", message);
        return ResponseEntity.ok(resultJson.toString());
    }
	
    
    /**
     * 取得資料
     * @param uid
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> get(
			@RequestParam("uid") String uid
			){
    	JSONObject resultJson = new JSONObject();
    	JSONArray itemList = new JSONArray();
    	boolean success = false;
    	String message = Constant.DATA_FOUNT;
    	
    	if(uid.isEmpty()) {
    		message = Constant.PARAM_EMPTY;
    	}else {
    		try {
    			VideoSubtitle videoSubtitle = service.getByUid(uid);;
	        	
    			JSONObject json = new JSONObject();
    			json.put("id",videoSubtitle.getId());
    			json.put("path",videoSubtitle.getPath());
    			json.put("uid",videoSubtitle.getUid());
    			itemList.put(json);
    		}catch(Exception e) {
				LOGGER.error(e.getMessage(), e);
    			message = Constant.ERROR;
    		}
    	}
    	resultJson.put("success", success);
    	resultJson.put("message", message);
    	resultJson.put("itemList", itemList);
    	return ResponseEntity.ok(resultJson.toString());
    }
    
    /**
     * 取得資料
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getSubtitle", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> getSubtitle(
			@RequestParam("uid") String uid
			){
		byte[] result = null;
    	InputStream is = null;
    	if(!uid.isEmpty()) {
    		try {
    			VideoSubtitle videoSubtitle = service.getByUid(uid);;
				if(videoSubtitle != null) {
					is = dataBucketUtil.downloadFile(videoSubtitle.getPath());
					if(is != null) {
						result = IOUtils. toByteArray(is);
					}
				}
    		}catch(Exception e) {
				LOGGER.error(e.getMessage(), e);
    		}
    	}
    	return ResponseEntity.ok(result);
    }
}
