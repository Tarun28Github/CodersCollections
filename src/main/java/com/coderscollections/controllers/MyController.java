package com.coderscollections.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coderscollections.entity.User;
import com.coderscollections.service.UserService;

@RestController

public class MyController {
	
	@Autowired
	private UserService service;
	
	 
	public MyController(UserService service) {
		super();
		this.service = service;
	}
	
	 @GetMapping("/me")
	    public ResponseEntity<User> authenticatedUser() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        User currentUser = (User) authentication.getPrincipal();

	        return ResponseEntity.ok(currentUser);
	    }
	
	@GetMapping("/home")
	public ResponseEntity<List<User>> getAllUser(){
		List<User> users=null;
		try {
			users = service.getAllUser();
					
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(users);
		
	}

	@GetMapping("/home/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") int id){
		User user=null;
		try {
			user = service.getUser(id);
					
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(user);
		
	}

	@PostMapping("/home")
	public ResponseEntity<String> addData(@RequestBody User user){
		try {
			service.addUser(user);
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("SomeThing Went Wrong");
		}
		return ResponseEntity.ok("Successfully added");
	}
	
	@DeleteMapping("/home/{id}")
	public ResponseEntity<Void> deleteUser( @PathVariable("id") int id){
		try {
			
			service.deleteUser(id);
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PutMapping("/home/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") int id , @RequestBody User user){
	    User updateUser=null;
		try {
			updateUser = service.updateUser(id, user);
			
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(updateUser);
	}
}
