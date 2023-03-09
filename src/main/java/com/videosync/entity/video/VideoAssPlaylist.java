package com.videosync.entity.video;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.videosync.entity.A_Entity;
import com.videosync.entity.I_Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "video_ass_playlist")
public class VideoAssPlaylist implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1558350093827081474L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable = false)
	private long vid;
	@Column(nullable = false)
	private long pid;
	@Column
	private int sort;
	
}
