package com.videosync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.videosync.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsernameAndPassword(String username, String password);
    List<User> findByUsername(String username);
    User findByUid(String uid);
    
}
