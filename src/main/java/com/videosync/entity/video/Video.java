package com.videosync.entity.video;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;

import com.videosync.dto.UserDto;
import com.videosync.entity.A_Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@Table(name = "video")
public class Video extends A_Entity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3463711763714606708L;

	@Column(nullable = false, unique = true, length = 36)
    private String uid;
    
    @Column( length = 2000)
    private String name;
    
    @Column( length = 2000)
    private String path;
    
    public Video(String name, String path, String uid, String userUid) {
        this.name = name;
        this.path = path;
        this.uid = uid;
        this.setCreate_date(new Date());
        this.setCreator(userUid);
    }
}
