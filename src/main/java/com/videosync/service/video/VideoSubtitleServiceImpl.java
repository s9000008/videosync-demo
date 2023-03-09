package com.videosync.service.video;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.videosync.Constant;
import com.videosync.entity.video.Video;
import com.videosync.entity.video.VideoSubtitle;
import com.videosync.repository.VideoSubtitleRepository;
import com.videosync.service.A_Service;
@Service
public class VideoSubtitleServiceImpl extends A_Service<VideoSubtitle> implements VideoSubtitleService{

	@Autowired
	private VideoServiceImpl videoService;
	
	@Autowired
	private VideoSubtitleRepository repo;
	
	@Override
	public VideoSubtitle getByUid(String uid) {
		return repo.getByUid(uid);
	}

	@Override
	public List<VideoSubtitle> getAllByVid(long vid) {
		return repo.getAllByVid(vid);
	}

	@Override
	public void saveSubtitle(
			MultipartFile file, 
			long vid, 
			String uid,
			String userUid) throws Exception  {
		Video video = videoService.get(vid);
		if(video == null) {
			new Exception(Constant.NON_DATA_EXISTS);
		}else if(video.getCreator().equals(uid)) {
			new Exception(Constant.NON_OWNER);
		}else if(file.isEmpty()) {
			new Exception(Constant.PARAM_EMPTY);
		}else{
			String filename = file.getOriginalFilename();
			String fileCode = java.util.UUID.randomUUID().toString();
			String extension = filename.substring(filename.lastIndexOf('.'));
			File serverFile = this.createFile((fileCode + extension));
			BufferedOutputStream stream = new BufferedOutputStream(
			    new FileOutputStream(serverFile));
			int length=0;
			byte[] buffer = new byte[1024];
			InputStream inputStream = file.getInputStream();
			while ((length = inputStream.read(buffer)) != -1) {
				stream.write(buffer, 0, length);
			}
			stream.flush();
			stream.close();
			VideoSubtitle row = new VideoSubtitle(fileCode, serverFile.getAbsolutePath(),userUid);
		    repo.save(row);
		}
	}
	
	
	private File createFile(String filename) {
		ClassLoader classLoader = getClass().getClassLoader();
		String folder = classLoader.getResource(".").getFile() + Constant.SUBTITLE_FOLDER;
		if(!new File(folder).exists()) {
			new File(folder).mkdirs();
		}
		String filepath = classLoader.getResource(".").getFile() + Constant.SUBTITLE_FOLDER + filename;
		filepath = filepath.substring(1);
		File file = new File(filepath);
		return file;
	}

}
