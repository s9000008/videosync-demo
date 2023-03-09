package com.videosync.repository;

import com.videosync.entity.video.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
	
    Video findByUid(String uid);

    boolean existsByUid(String uid);
    
    @Query(nativeQuery = true, value="SELECT name FROM video")
    List<String> getAllEntryNames();
    
    @Query(nativeQuery = true, value="SELECT id, uid, name, extension, path, size, creator FROM video")
    List<Video> getAllVideo();
    
    @Query(nativeQuery = true, value="SELECT id, name, path FROM video")
    List<Video> getAllVideoResource();
    
    
    @Query(nativeQuery = true, value="SELECT id, name, path FROM video")
    List<Video> getVideoByPage();
    
    
}
