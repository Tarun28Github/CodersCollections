package com.coderscollections.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscollections.dao.UserRepository;
import com.coderscollections.entity.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	public List<User> getAllUser() {
        List<User> all = (List<User>)userRepository.findAll();
        return all;
	}
	public User getUser(int id) {
         Optional<User> optional = userRepository.findById(id);
         User user = optional.get();
         return user;
	}
	
	public void addUser(User user) {
		User save = userRepository.save(user);
	}
	
	public void deleteUser(int id) {
		Optional<User> byId = userRepository.findById(id);
		User user = byId.get();
		userRepository.delete(user);
	}
	public User updateUser(int id, User user) {
		
		user.setId(id);
		User save = userRepository.save(user);
		return save;
	}
}
