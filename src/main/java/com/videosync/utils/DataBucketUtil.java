package com.videosync.utils;

import com.videosync.Constant;
import com.videosync.dto.FileDto;
import com.videosync.entity.video.Video;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.cloud.storage.StorageOptions;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataBucketUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBucketUtil.class);

    @Value("${gcp.config.file}")
    private String gcpConfigFile;

    @Value("${gcp.project.id}")
    private String gcpProjectId;

    @Value("${gcp.bucket.id}")
    private String gcpBucketId;

    @Value("${gcp.dir.name}")
    private String gcpDirectoryName;

    @Value("${gcp.storage.url}")
    private String baseurl;

    public FileDto uploadFile(MultipartFile multipartFile, String fileName, String contentType) throws Exception {

        File file = convertFile(multipartFile);
        InputStream resourceStream = new FileInputStream(file);
        InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();

        StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                .setCredentials(GoogleCredentials.fromStream(inputStream)).build();

        Storage storage = options.getService();

        Bucket bucket = storage.get(gcpBucketId,Storage.BucketGetOption.fields());

        RandomString id = new RandomString(6, ThreadLocalRandom.current());
        Blob blob = bucket.create(gcpDirectoryName + "/" + fileName, resourceStream, contentType);
        if(blob != null){
            String mediaLink = blob.getMediaLink();
            mediaLink = baseurl + mediaLink.substring(mediaLink.indexOf("/o/") + 1).replace("/o/", "/");
            return new FileDto(blob.getName(), mediaLink);
        }
        throw new Exception("GCS檔案上傳過程發生錯誤");
    }
    
    public InputStream downloadFile(String fileUrl) throws Exception {
        String filename = fileUrl.replace("/o/", "/");
        filename = filename.substring(0,filename.lastIndexOf("?"));
        filename = filename.substring(filename.indexOf(gcpBucketId));
        filename = filename.replace(gcpBucketId + "/", "");
    	InputStream is = null;
    	//fileUrl = fileUrl.replace(baseurl,"");
        filename = java.net.URLDecoder.decode(filename, StandardCharsets.UTF_8.toString());
        InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();
        StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
        Storage storage = options.getService();

        //BlobListOption blobListopt = Storage.BlobListOption.currentDirectory();
        //blobListopt.prefix(filename)

        //Blob blob = storage.get(gcpBucketId,filename);
        //"resource/[SC-OL][Shakugan no Shana III][04][MKV][X264_FLAC][BDRip].big5.ass"
        //filename = "resource/[SC-OL][Shakugan no Shana III][01][MKV][X264_FLAC][BDRip].big5.ass";
        Blob blob = storage.get(BlobId.of(gcpBucketId, filename));
        Storage.BlobGetOption option ;
        /*storage.signUrl(BlobInfo.newBuilder(BUCKET_NAME, fileName).build(),
                1, TimeUnit.DAYS, SignUrlOption.signWith(ServiceAccountCredentials.fromStream(
                        new FileInputStream(PATH_TO_JSON_KEY))));*/
        if(blob != null) {
            ReadChannel  readChannel = blob.reader();
            is = Channels.newInputStream(readChannel);
        }

    	return is;
    }
    private File convertFile(MultipartFile file) throws Exception {

            if(file.getOriginalFilename() == null){
                throw new Exception("檔案名稱為空");
            }
            File convertedFile = new File(file.getOriginalFilename());
            /*FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write(file.getBytes());
            outputStream.close();*/

				//File serverFile = this.createFile((fileCode + extension));
				//System.out.println("exists:" + serverFile.exists());
            BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(convertedFile));
            int length=0;
            byte[] buffer = new byte[1024];
            InputStream inputStream = file.getInputStream();
            while ((length = inputStream.read(buffer)) != -1) {
                stream.write(buffer, 0, length);
            }
            stream.flush();
            stream.close();
            return convertedFile;
    }

    private String checkFileExtension(String fileName) throws Exception {
        if(fileName != null && fileName.contains(".")){
            String[] extensionList = {".png", ".jpeg", ".pdf", ".doc", ".mp3","mp4","ass","vtt","mkv"};

            for(String extension: extensionList) {
                if (fileName.endsWith(extension)) {
                    LOGGER.info("檔案擴展名 : {}", extension);
                    return extension;
                }
            }
        }
        LOGGER.error("Not a permitted file type");
        throw new Exception("Not a permitted file type");
    }
}
