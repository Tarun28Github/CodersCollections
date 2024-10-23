package com.coderscollections.jwtConfig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.coderscollections.jwt.JwtAuthenticationEntryPoint;
import com.coderscollections.jwt.JwtAuthenticationFilter;



@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationEntryPoint point;
	@Autowired
	private AuthenticationProvider authenticationProvider;
	@Autowired
	private JwtAuthenticationFilter filter;
	
	
    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf->csrf.disable())
		.cors(cors->cors.disable())
		.authorizeHttpRequests(auth-> auth.requestMatchers("/auth/**").
				 permitAll().anyRequest().authenticated())
		.exceptionHandling(ex->ex.authenticationEntryPoint(point))
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authenticationProvider(authenticationProvider);
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
    
	/*
	 * @Bean public UrlBasedCorsConfigurationSource corsConfigurationSource() {
	 * CorsConfiguration configuration = new CorsConfiguration();
	 * 
	 * configuration.setAllowedOrigins(List.of("http://localhost:8080"));
	 * configuration.setAllowedMethods(List.of("GET","POST"));
	 * configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
	 * 
	 * UrlBasedCorsConfigurationSource source = new
	 * UrlBasedCorsConfigurationSource();
	 * 
	 * source.registerCorsConfiguration("/**",configuration);
	 * 
	 * return source; }
	 */

}
