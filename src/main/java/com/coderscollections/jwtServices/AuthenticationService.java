package com.coderscollections.jwtServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coderscollections.dao.UserRepository;
import com.coderscollections.entity.User;
import com.coderscollections.jwtdtos.LoginUserDtos;
import com.coderscollections.jwtdtos.RegisterUserDtos;

@Service
public class AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private  PasswordEncoder passwordEncoder;

	@Autowired
	private  AuthenticationManager authenticationManager;

	public User signup(RegisterUserDtos input) {
		User user = new User();
		user.setName(input.getName());
		user.setEmail(input.getEmail());
		user.setPassword(passwordEncoder.encode(input.getPassword()));

		return userRepository.save(user);
	}

	public User authenticate(LoginUserDtos input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		return userRepository.findByEmail(input.getEmail()).orElseThrow();
	}
}
