package com.videosync.service.video;
import com.videosync.entity.video.Video;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
public interface VideoService {
	Video getVideo(String name) throws Exception;

	void saveVideo(Video video) throws Exception;
	
    void saveVideo(MultipartFile file, String name, String creator) throws Exception;

    List<Video> getAllVideo();
    
    List<Video> getVideoByPage(int size, int pageSize);
}
