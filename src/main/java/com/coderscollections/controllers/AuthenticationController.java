package com.coderscollections.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderscollections.entity.User;
import com.coderscollections.jwt.JwtHelper;
import com.coderscollections.jwtServices.AuthenticationService;
import com.coderscollections.jwtdtos.LoginResponse;
import com.coderscollections.jwtdtos.LoginUserDtos;
import com.coderscollections.jwtdtos.RegisterUserDtos;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

	@Autowired
    private JwtHelper jwtHelper;
	@Autowired
    private AuthenticationService authenticationService;

    public AuthenticationController(JwtHelper jwtHelper, AuthenticationService authenticationService) {
        this.jwtHelper = jwtHelper;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDtos registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDtos loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtHelper.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtHelper.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
