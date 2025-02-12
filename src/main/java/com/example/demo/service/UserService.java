package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();//登録されたデータベースを返す
    }

	public User saveUser(User user) {
		// TODO 自動生成されたメソッド・スタブ
	    return userRepository.save(user); // 保存されたエンティティを返す	
	}
	
	public User findUserById(Long id) {
	    return userRepository.findById(id).orElse(null);//引数で受け取ったidのエンティティを返す
	}
}
