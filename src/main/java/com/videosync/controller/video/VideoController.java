package com.videosync.controller.video;
import lombok.AllArgsConstructor;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.videosync.Constant;
import com.videosync.dto.UserDto;
import com.videosync.dto.VideoDto;
import com.videosync.entity.user.User;
import com.videosync.entity.video.Video;
import com.videosync.service.FileServiceImpl;
import com.videosync.service.video.VideoServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * 影音資料
 * @author Tenghung
 *
 */
@RestController
@RequestMapping("api/video")
@AllArgsConstructor
public class VideoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

	@Autowired
    private VideoServiceImpl service;
	
	@Autowired
	private FileServiceImpl fileService;
	
	/**
	 * 新增
	 * @param files
	 * @param name
	 * @param user
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<String> save(
    		@RequestParam("file") MultipartFile[] files, 
    		@RequestParam("name") String name,
    		@SessionAttribute("userRow") UserDto user
    		) throws Exception {
    	JSONObject resultJson = new JSONObject();
    	boolean success = false;
    	String message = Constant.SAVE_SUCCESS;
    	if(user == null) {
    		message = Constant.NON_LOGIN;
    	}else {
    		if(files.length == 0 ) {
    			message = Constant.PARAM_EMPTY;
    		}else if(files.length > 2){
    			message = Constant.ERROR;
    		}else {
    			MultipartFile file = files[0];
				if(file.isEmpty()) {
		    		message = Constant.PARAM_EMPTY;
		    	}else {
		    		String filename = file.getOriginalFilename();
		    		message = Constant.EXTENSION_NOT_SUPPORT;
		    		if(filename.endsWith(".mp4") || filename.endsWith(".mkv")) {
			    		try {
			    			fileService.uploadFiles(files, user.getUid());
			    			//service.saveVideo(file, name, user.getUid());
			    			message = Constant.SAVE_SUCCESS;
			    			success = true;
			    		}catch(Exception e) {
							message = Constant.ERROR;
							LOGGER.error(e.getMessage(), e);
			    		}
		    		}
		    	}
    		}
	    	
    	}
    	resultJson.put("success", success);
    	resultJson.put("message", message);
        return ResponseEntity.ok(resultJson.toString());
    }
    /**
     * 更新
     * @param payload
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<String> update(
    		@RequestBody Map<String, Object> payload,
    		@SessionAttribute("userRow") User user
    		) throws Exception {
    	JSONObject resultJson = new JSONObject();
    	boolean success = false;
    	String message = Constant.UPDATE_SUCCESS;
    	if(user == null) {
    		message = Constant.NON_LOGIN;
    	}else {
			try {
				Long id = Long.parseLong((String) payload.getOrDefault("id", "0"));
				String name = (String) payload.getOrDefault("name", "0");
				Video video = service.get(id);
				if (video == null) {
					message = Constant.DATA_NOT_FOUNT;
				} else if (!video.getUid().equals(user.getUid())) {
					message = Constant.NON_OWNER;
				} else {
					video.setName(name);
					service.save(video);
					success = true;
				}
			}catch(Exception e){
				message = Constant.ERROR;
				LOGGER.error(e.getMessage(), e);
			}
    	}
    	resultJson.put("success", success);
    	resultJson.put("message", message);
        return ResponseEntity.ok(resultJson.toString());
    }
    
    /**
     * 刪除
     * @param payload
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<String> delete(
    		@RequestBody Map<String, Object> payload,
    		@SessionAttribute("userRow") UserDto user
    		) throws Exception {
    	JSONObject resultJson = new JSONObject();
    	boolean success = false;
    	String message = Constant.DELETE_SUCCESS;
		//TODO: 影片資源移除
    	Long id = Long.parseLong((String)payload.getOrDefault("id", "0"));
    	Video video = service.get(id);
    	if(user == null) {
    		message = Constant.NON_LOGIN;
    	} else {
			try {
				if (!video.getUid().equals(user.getUid())) {
					message = Constant.NON_OWNER;
				} else {
					service.delete(video);
					success = true;
				}
			}catch(Exception e){
				message = Constant.ERROR;
				LOGGER.error(e.getMessage(), e);
			}
    	}
    	resultJson.put("success", success);
    	resultJson.put("message", message);
        return ResponseEntity.ok(message);
    }
    
    /**
     * 取得全部資料
     * @param user
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getAll(
			@SessionAttribute("userRow") UserDto user
			){
    	JSONObject resultJson = new JSONObject();
    	JSONArray itemList = new JSONArray();
    	boolean success = false;
    	String message = Constant.DATA_FOUNT;
    	List<Video> videolist = service.getAll();
		try {
			if (videolist.isEmpty() || videolist.size() == 0) {
				message = Constant.DATA_NOT_FOUNT;
			} else {
				for (Video video : videolist) {
					VideoDto videoRow = new VideoDto(video);
					itemList.put(videoRow.toJson());
				}
				success = true;
			}
		}catch(Exception e){
			message = Constant.ERROR;
			LOGGER.error(e.getMessage(), e);
		}
    	resultJson.put("success", success);
    	resultJson.put("message", message);
    	resultJson.put("itemList", itemList);
    	return ResponseEntity.ok(resultJson.toString());
    }
    /*public List<VideoDto> getAllVideoNames(){
    	List<VideoDto> result = new ArrayList<VideoDto>();
    	List<Video> videos = videoService.getAll();
    	for(Video video : videos) {
    		VideoDto row = new VideoDto(video);
    		result.add(row);
    	}
        return result;
    }*/
}
