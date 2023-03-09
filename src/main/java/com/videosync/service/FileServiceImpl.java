package com.videosync.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.videosync.dto.FileDto;
import com.videosync.entity.video.Video;
import com.videosync.entity.video.VideoSubtitle;
import com.videosync.repository.VideoSubtitleRepository;
import com.videosync.utils.DataBucketUtil;

import com.videosync.service.video.VideoService;
@Service
public class FileServiceImpl implements FileService{
	@Autowired
	private VideoService service;
	@Autowired
    private DataBucketUtil dataBucketUtil;
	@Autowired
	private VideoSubtitleRepository videoSubtitleRepo;
	@Override
	public void uploadFiles(
			MultipartFile[] files, 
			String userUid) throws Exception{
		String fileCode = java.util.UUID.randomUUID().toString();
		Arrays.asList(files).forEach(file -> {
            String originalFileName = file.getOriginalFilename();
            if(originalFileName != null){
            	try {
		            Path path = new File(originalFileName).toPath();
		            
		            String filename = fileCode + originalFileName.substring(originalFileName.lastIndexOf("."));
	                String contentType = Files.probeContentType(path);
	                
	                FileDto fileDto = dataBucketUtil.uploadFile(file, filename, contentType);
	                
	                if (fileDto != null) {
	                	if(originalFileName.endsWith("ass") || originalFileName.endsWith("vtt")) {
		                    VideoSubtitle row = new VideoSubtitle(fileCode,fileDto.getFileUrl(),userUid);
		                    videoSubtitleRepo.save(row);
		                }else {
		                	String extName = originalFileName.substring(originalFileName.indexOf(".") + 1);
		                	System.out.println(fileDto.getFileName());
		                	System.out.println(fileDto.getFileUrl());
		                	Video video = new Video(filename, fileDto.getFileUrl(), fileCode, userUid);
		                    service.saveVideo(video);
		                }
	                }
            	}catch(Exception e) {
            		e.printStackTrace();
            		new Exception("發生錯誤");
            	}
            }
        });
    }

}
