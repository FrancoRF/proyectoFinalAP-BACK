package com.example.demo.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.example.demo.seguridad.jwt.JwtEntryPoint;
import com.example.demo.seguridad.jwt.JwtTokenFilter;
import com.example.demo.seguridad.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSeguridad extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsServiceImpl userImpl;
	
	@Autowired
	JwtEntryPoint jwtEntry;
	
	public JwtTokenFilter jwtTokenFilter() {
		
	}

}
