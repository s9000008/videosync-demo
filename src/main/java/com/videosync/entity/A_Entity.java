package com.videosync.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
@MappedSuperclass
@Data
public abstract class A_Entity implements I_Entity, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8594513577706618870L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false, length = 100)
    private String creator;
	
	@Column
    private Date create_date;
	
	@Column(nullable = true, length = 100)
    private String updater;
	
	@Column
    private Date update_date;
	
}
