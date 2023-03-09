package com.videosync.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void uploadFiles(MultipartFile[] files, String userUid) throws Exception;
}
