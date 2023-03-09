package com.videosync.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

/**
 * 停用 改用GCP
 * @author Tenghung
 *
 */
@Service
public class StreamingService {
	//private static final String FORMAT = "classpath:/videofolder/%s";
	//private static final String FORMAT = "classpath:videofolder/%s";
	private static final String FORMAT = "videofolder/%s";
	@Autowired
	private ResourceLoader resourceLoader;
	
	public Mono<Resource> getVideo(String title) {
		try {
			title = URLDecoder.decode(title, "UTF-8").replace("%20", " ");
			System.out.println(title);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String videoPath = String.format(FORMAT, title);
		//Resource resource = resourceLoader.getResource("classpath:/" + videoPath);
		InputStream is = resourceLoader.getClassLoader().getResourceAsStream(videoPath);;
		//resourceLoader.getClassLoader().getResourceAsStream("classpath:/" + videoPath);
		Resource res = new InputStreamResource(is);
		//org.springframework.core.io.Resource r = new Resource(is);
		/*try {
			//System.out.println(resource.contentLength());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return Mono.fromSupplier(
				() -> res//resourceLoader.getResource(videoPath)
				);
	}
	//Mono<Resource>
	public InputStream getVideoInputstream(String title) throws IOException{
		
		try {
			title = URLDecoder.decode(title, "UTF-8").replace("%20", " ");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String videoPath = String.format(FORMAT, title);
		InputStream is = resourceLoader.getClassLoader().getResourceAsStream(videoPath);
		return is;
	}
	
	public long getVideoRange(String title) throws IOException{
		try {
			title = URLDecoder.decode(title, "UTF-8").replace("%20", " ");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String videoPath = String.format(FORMAT, title);
		InputStream is = resourceLoader.getClassLoader().getResourceAsStream(videoPath);
		return (long)is.available();
	}
}
