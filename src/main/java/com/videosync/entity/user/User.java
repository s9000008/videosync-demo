package com.videosync.entity.user;

import java.io.Serializable;
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
@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4017666025398090588L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "uid")
    private String uid;
    @Column(name = "created_date")
    private Date createdDate;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.uid = java.util.UUID.randomUUID().toString();
        this.createdDate = new Date();
    }
    
    public User(String username, String password, String uid) {
        this.username = username;
        this.password = password;
        this.uid = uid;
        this.createdDate = new Date();
    }
}
