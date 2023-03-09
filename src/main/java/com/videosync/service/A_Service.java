package com.videosync.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;


public abstract class A_Service<T> implements I_Service<T>{
	@Autowired
	JpaRepository<T, Long> repo;
	@Override
	public JpaRepository<T, Long> getRepo(){
		return repo;
	}
	@Override
	public T get(long id) {
		return repo.getReferenceById(id);
	}
	@Override
	public T save(T t) {
		return repo.save(t);
	}
	@Override
	public T update(T t) {
		return repo.save(t);
	}
	@Override
	public void delete(T t) {
		repo.delete(t);
	}
	@Override
	public void delete(long id) {
		repo.deleteById(id);
	}
	@Override
	public boolean exists(long id) {
		return repo.existsById(id);
	}

	@Override
	public List<T> getAll() {
		return repo.findAll();
	}
	@Override
	public Page<T> getPage(PageRequest pageRequest){
		return repo.findAll(pageRequest);
	}
}
