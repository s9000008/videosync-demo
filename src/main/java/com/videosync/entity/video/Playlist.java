package com.videosync.entity.video;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.videosync.entity.A_Entity;
@Entity
@Data
@NoArgsConstructor
@Table(name = "playlist")
public class Playlist extends A_Entity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6657443709901386255L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column
	private String name;
	@Column(unique = true, length = 36)
	private String uid;
	
    public Playlist(String name, String uid, String creator) {
    	this.name = name;
    	this.uid = uid;
    	this.setCreator(creator);
    	this.setCreate_date(new Date());
    }
    
}
