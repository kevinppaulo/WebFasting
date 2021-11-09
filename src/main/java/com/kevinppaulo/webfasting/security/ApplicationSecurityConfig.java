package com.kevinppaulo.webfasting.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

	private final LogoutHandler logoutHandler;

	@Autowired
	public ApplicationSecurityConfig(LogoutHandler logoutHandler){
		this.logoutHandler = logoutHandler;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.mvcMatchers("/").permitAll()
			.anyRequest().authenticated()
			.and().oauth2Login()
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.addLogoutHandler(logoutHandler);
	
	}
	
}
