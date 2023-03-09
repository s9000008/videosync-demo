package com.videosync.controller.video;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.videosync.Constant;
import com.videosync.dto.UserDto;
import com.videosync.entity.user.User;
import com.videosync.entity.video.Playlist;
import com.videosync.entity.video.Video;
import com.videosync.entity.video.VideoAssPlaylist;
import com.videosync.service.video.VideoAssPlaylistServiceImpl;
import com.videosync.service.video.VideoServiceImpl;
import com.videosync.service.user.UserService;
import com.videosync.service.video.PlaylistServiceImpl;
import lombok.AllArgsConstructor;

/**
 * 播放清單
 * @author Tenghung
 *
 */
@RestController
@RequestMapping("api/playlist")
@AllArgsConstructor
public class PlaylistController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlaylistController.class);

	@Autowired
	private PlaylistServiceImpl service;
	
	@Autowired
	private VideoAssPlaylistServiceImpl videoAssPlaylistService;
	
	@Autowired
	private VideoServiceImpl videoService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 新增
	 * @param payload
	 * @param userdto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<String> save(
    		@RequestBody Map<String, Object> payload,
    		@SessionAttribute("userRow") UserDto userdto
    		) throws Exception {
		JSONObject resultJson = new JSONObject();
		boolean success = false;
    	String message = Constant.SAVE_SUCCESS;
    	String name = (String)payload.getOrDefault("name", "");
    	User user = null;
		try {
			if (userdto != null) {
				user = userService.findByUid(userdto.getUid());
			}
			if (user == null) {
				message = Constant.NON_LOGIN;
			} else if (name.trim().isEmpty()) {
				message = Constant.PARAM_EMPTY;
			} else {
				Playlist playlist = new Playlist(name, UUID.randomUUID().toString(), user.getUid());
				service.save(playlist);
				success = true;
			}
		}catch(Exception e){
			message = Constant.ERROR;
			LOGGER.error(e.getMessage(), e);
		}
    	resultJson.put("message", message);
    	resultJson.put("success", success);
        return ResponseEntity.ok(resultJson.toString());
    }

	/**
	 * 更新
	 * @param payload
	 * @param userdto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<String> update(
    		@RequestBody Map<String, Object> payload,
    		@SessionAttribute("userRow") UserDto userdto
    		) throws Exception {
		JSONObject resultJson = new JSONObject();
		boolean success = false;
    	String message = Constant.UPDATE_SUCCESS;
    	User user = null ;
		try {
			if (userdto != null) {
				user = userService.findByUid(userdto.getUid());
			}
			Long id = Long.parseLong((String) payload.getOrDefault("id", "0"));
			String name = (String) payload.getOrDefault("name", "");
			String uid = (String) payload.getOrDefault("uid", "");
			Playlist playlist = service.get(id);
			if (user == null) {
				message = Constant.NON_LOGIN;
			} else if (playlist == null) {
				message = Constant.DATA_NOT_FOUNT;
			} else if (!playlist.getUid().equals(uid)) {
				message = Constant.NON_OWNER;
			} else {
				playlist.setName(name);
				playlist = service.update(playlist);
				success = playlist != null;
			}
		}catch(Exception e){
			message = Constant.ERROR;
			LOGGER.error(e.getMessage(), e);
		}
    	resultJson.put("message", message);
    	resultJson.put("success", success);
        return ResponseEntity.ok(resultJson.toString());
    }
	
	/**
	 * 取得全部
	 * @param userdto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
    public List<Playlist> getAll(
    		@SessionAttribute("userRow") UserDto userdto
    		) throws Exception {
		User user = null;
		List<Playlist> result = null;
		if(userdto != null) {
    		user = userService.findByUid(userdto.getUid());
    	}
		if(user == null) {
			result = new ArrayList<Playlist>();
		}else {
			result = service.getPlaylistByCreator(userdto.getUid());
		}
        return result;
    }
	
	/**
	 * 取得單筆
	 * @param uid
	 * @param userdto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
    public ResponseEntity<String> getPlaylist(
    		@RequestParam String uid,
    		@SessionAttribute("userRow") UserDto userdto
    		) throws Exception {
		JSONObject resultJson = new JSONObject();
		boolean success = false;
    	String message = Constant.DATA_FOUNT;
    	User user = null;
		try {
			if (userdto != null) {
				user = userService.findByUid(userdto.getUid());
			}
			if (user == null) {
				message = Constant.NON_LOGIN;
			} else if (uid.trim().isEmpty()) {
				message = Constant.PARAM_EMPTY;
			} else {
				Playlist playlist = service.getPlaylistByUid(uid);
				if (playlist == null) {
					message = Constant.DATA_NOT_FOUNT;
				} else {
					JSONObject playlistJson = new JSONObject();
					JSONArray videoArray = new JSONArray();
					playlistJson.put("uid", playlist.getUid());
					playlistJson.put("name", playlist.getName());
					playlistJson.put("id", playlist.getId());
					List<VideoAssPlaylist> videoAssList = videoAssPlaylistService.getByPid(playlist.getId());
					for (VideoAssPlaylist item : videoAssList) {
						JSONObject videoJson = new JSONObject();
						Video video = videoService.getById(item.getVid());
						videoJson.put("id", video.getId());
						videoJson.put("aid", item.getId());
						videoJson.put("name", video.getName());
						videoJson.put("uid", video.getUid());
						videoJson.put("sort", item.getSort());
						videoArray.put(videoJson);
					}
					playlistJson.put("itemList", videoArray);
					resultJson.put("item", playlistJson);
				}
				success = true;
			}
		}catch(Exception e){
			message = Constant.ERROR;
			LOGGER.error(e.getMessage(), e);
		}
    	resultJson.put("message", message);
    	resultJson.put("success", success);
        return ResponseEntity.ok(resultJson.toString());
    }
	
	/**
	 * 新增影片
	 * @param payload
	 * @param userdto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<String> addVideo(
    		@RequestBody Map<String, Object> payload,
    		@SessionAttribute("userRow") UserDto userdto
    		) throws Exception {
		JSONObject resultJson = new JSONObject();
		boolean success = false;
    	String message = Constant.SAVE_SUCCESS;
    	Long id = Long.parseLong((String)payload.getOrDefault("id", "0"));
    	Integer sort = (Integer)payload.getOrDefault("sort", 0);
    	Long pid = Long.parseLong((String)payload.getOrDefault("pid", "0"));
    	Long vid = Long.parseLong((String)payload.getOrDefault("vid", "0"));
    	//String vUid = (String)payload.getOrDefault("vuid", "");
		try {
			User user = null;
			if (userdto != null) {
				user = userService.findByUid(userdto.getUid());
			}
			if (user == null) {
				message = Constant.NON_LOGIN;
			} else if (pid == 0) {
				message = Constant.PARAM_EMPTY;
			} else if (vid == 0) {
				message = Constant.PARAM_EMPTY;
			} else {
				VideoAssPlaylist row = new VideoAssPlaylist();
				row.setId(id);
				row.setSort(sort);
				row.setPid(pid);
				row.setVid(vid);
				videoAssPlaylistService.save(row);
				success = true;
			}
		}catch(Exception e){
			message = Constant.ERROR;
			LOGGER.error(e.getMessage(), e);
		}
    	resultJson.put("message", message);
    	resultJson.put("success", success);
        return ResponseEntity.ok(resultJson.toString());
    }
	
	/**
	 * 刪除影片
	 * @param payload
	 * @param userdto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<String> removeVideo(
    		@RequestBody Map<String, Object> payload,
    		@SessionAttribute("userRow") UserDto userdto
    		) throws Exception {
		JSONObject resultJson = new JSONObject();
		boolean success = false;
    	String message = Constant.DELETE_SUCCESS;
    	try {
	    	Long id = Long.parseLong(String.valueOf(payload.getOrDefault("id", "0")));
	    	User user = null;
	    	if(userdto != null) {
	    		user = userService.findByUid(userdto.getUid());
	    	}
	    	if(user == null) {
	    		message = Constant.NON_LOGIN;
	    	}else if(id == 0) {
	    		message = Constant.PARAM_EMPTY;
	    	}else {
	    		VideoAssPlaylist row = videoAssPlaylistService.get(id);
	    		if(row != null) {
	    			videoAssPlaylistService.delete(row);
	    		}
	    		success = true;
	    	}
    	}catch(Exception e) {
			message = Constant.ERROR;
			LOGGER.error(e.getMessage(), e);
    	}
    	resultJson.put("message", message);
    	resultJson.put("success", success);
        return ResponseEntity.ok(resultJson.toString());
    }
	/**
	 * 刪除影片
	 * @param payload
	 * @param userdto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sort", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<String> sortVideo(
    		@RequestBody Map<String, Object> payload,
    		@SessionAttribute("userRow") UserDto userdto
    		) throws Exception {
		JSONObject resultJson = new JSONObject();
		boolean success = false;
    	String message = Constant.UPDATE_SUCCESS;
    	User user = null;
    	if(userdto != null) {
    		user = userService.findByUid(userdto.getUid());
    	}
    	if(
    			!payload.containsKey("aid") ||
    			!payload.containsKey("sort")) {
    		message = Constant.PARAM_EMPTY;
    	}else if(user == null){
    		message = Constant.NON_LOGIN;
    	}else {
	    	try {
		    	Long id = Long.parseLong(String.valueOf(payload.getOrDefault("aid", "0")));
		    	int sortNum = Integer.parseInt(String.valueOf(payload.getOrDefault("sort", 0)));
	    		VideoAssPlaylist row = videoAssPlaylistService.get(id);
	    		if(sortNum != 0 && row != null) {
	    			long pid = row.getPid();
	    			boolean sortPlus = sortNum > 0;
	    			List<VideoAssPlaylist> playlist = videoAssPlaylistService.getByPid(pid);
	    			if(playlist.size() > 1) {
	    				int itemIndex = -1;
		    			int changItemIndex = -1;
		    			for(int i = 0; i < playlist.size(); i++) {
		    				if(itemIndex == -1 && changItemIndex == -1) {
			    				VideoAssPlaylist item = playlist.get(i);
			    				if(item.getId() == id) {
			    					itemIndex = i;
			    					if(playlist.size() - 1 == i) {
			    						changItemIndex = (i-1);
			    					}else {
			    						changItemIndex = (i+1);
			    					}
			    				}
		    				}
		    			}
		    			VideoAssPlaylist item = playlist.get(itemIndex);
		    			VideoAssPlaylist changItem = playlist.get(changItemIndex);
		    			if(sortPlus) {
		    				int itemSortNum = item.getSort() + 1;
		    				itemSortNum = (itemSortNum >= playlist.size() ? playlist.size() : itemSortNum);
		    				changItem.setSort(item.getSort());
		    				videoAssPlaylistService.update(changItem);
		    				item.setSort(itemSortNum);
		    				videoAssPlaylistService.update(item);
		    			}else {
		    				int itemSortNum = item.getSort() - 1;
		    				itemSortNum = (itemSortNum <= 0 ? 0 : itemSortNum);
		    				changItem.setSort(item.getSort());
		    				videoAssPlaylistService.update(changItem);
		    				item.setSort(itemSortNum);
		    				videoAssPlaylistService.update(item);
	    				}
	    			}
    			}
	    		success = true;
	    	}catch(Exception e) {
				message = Constant.ERROR;
				LOGGER.error(e.getMessage(), e);
	    	}
    	}
    	resultJson.put("message", message);
    	resultJson.put("success", success);
        return ResponseEntity.ok(resultJson.toString());
    }
}
