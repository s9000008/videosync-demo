package com.videosync.service.video;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.videosync.Constant;
import com.videosync.entity.video.Video;
import com.videosync.repository.VideoRepository;
import com.videosync.service.A_Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Service
public class VideoServiceImpl extends A_Service<Video> implements VideoService{



	@Autowired
	private VideoRepository repo;
	
	@Override
	public Video getVideo(String uid) throws Exception {
		return repo.findByUid(uid);
	}

	@Override
	public List<Video> getAllVideo() {
		return repo.getAllVideo();
	}

	@Override
	public void saveVideo(
			MultipartFile file, 
			String name, 
			String creator
			) throws Exception {
		if (!file.isEmpty()) {
			String filename = file.getOriginalFilename();
			String fileCode = java.util.UUID.randomUUID().toString();
			String extension = filename.substring(filename.lastIndexOf('.'));
			try {
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
				Video video = new Video(name, serverFile.getAbsolutePath(), fileCode, creator);
			    repo.save(video);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Video getById(long id) {
		return repo.getReferenceById(id);
	}
	
	
	public void delete() {
		repo.delete(null);
	}
	
	
	private File createFile(String filename) {
		ClassLoader classLoader = getClass().getClassLoader();
		String folder = classLoader.getResource(".").getFile() + Constant.VIDEO_FOLDER;
		if(!new File(folder).exists()) {
			new File(folder).mkdirs();
		}
		String filepath = classLoader.getResource(".").getFile() + Constant.VIDEO_FOLDER + filename;
		filepath = filepath.substring(1);
		File file = new File(filepath);
		return file;
	}

	@Override
	public List<Video> getVideoByPage(int size, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveVideo(Video video) throws Exception {
		repo.save(video);
	}
	
}
