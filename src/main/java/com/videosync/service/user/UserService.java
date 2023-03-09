package com.videosync.service.user;

import java.util.List;

import com.videosync.entity.user.User;
public interface UserService {
    public User findByUsernameAndPassword(String username, String password);
    public User findByUid(String uid) ;
    public List<User> findByUsername(String username);
}
