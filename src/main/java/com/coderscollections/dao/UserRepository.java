package com.coderscollections.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coderscollections.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	public Optional<User> findByEmail(String email);

}
