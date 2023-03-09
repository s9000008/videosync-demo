package com.videosync.entity.video;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.videosync.entity.A_Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@Table(name = "video_subtitle")
public class VideoSubtitle extends A_Entity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8635285012559365150L;

	@Column(unique = true, length = 36)
    private String uid;
	
	@Column(length = 2000)
    private String path;
	
	public VideoSubtitle(String uid,String path, String creator) {
		this.uid = uid;
		this.path = path;
		this.setCreator(creator);
		this.setCreate_date(new Date());
	}
	
}
