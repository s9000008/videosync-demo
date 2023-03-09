package com.videosync.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface I_Service<T> {

	JpaRepository<T, Long> getRepo();
	
	T get(long id);
	
	T save(T t);
	
	T update(T t);
	
	void delete(T t);
	
	void delete(long id);
	
	boolean exists(long id);
	
	List<T> getAll();

	Page<T> getPage(PageRequest pageRequest);

}
