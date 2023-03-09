package com.videosync.service.user;

import java.util.List;
import com.videosync.entity.user.User;
import com.videosync.repository.UserRepository;
import com.videosync.service.A_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends A_Service<User> implements UserService{
	
	@Autowired
    private UserRepository userRepo;

    public User findByUsernameAndPassword(String username, String password) {
        return userRepo.findByUsernameAndPassword(username, password);
    }

    
    public User findByUid(String uid) {
    	return userRepo.findByUid(uid);
    }

    public List<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
    
    public List<User> login(
    		String username, 
    		String password){
    	List<User> list;
    	list = userRepo.findByUsername(username);
    	if(list == null) {
    		throw new IllegalArgumentException("登入失敗，找不到帳號");
    	}else {
    		User user = list.get(0);
    		if(!user.getPassword().equals(password)) {
    			throw new IllegalArgumentException("密碼輸入錯誤，請重新輸入");
    		}
    	}
    	return list;
    }
	
}
