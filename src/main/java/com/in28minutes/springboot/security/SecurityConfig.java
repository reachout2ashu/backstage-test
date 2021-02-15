package com.in28minutes.springboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// Authentication : User --> Roles
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance()).withUser("user1").password("secret1")
				.roles("USER").and().withUser("admin1").password("secret1")
				.roles("USER", "ADMIN");
	}

	// Authorization : Role -> Access
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests()
		.antMatchers(
		          HttpMethod.GET,
		          "/v2/api-docs",
		          "/swagger-resources/**",
		          "/swagger-ui.html**",
		          "/webjars/**",
		          "favicon.ico"
		        ).permitAll()
		.antMatchers("/students/**")
				.hasRole("USER").antMatchers("/**").hasRole("ADMIN").and()
				.csrf().disable().headers().frameOptions().disable();
		
		//http.authorizeRequests().anyRequest().permitAll();
	}

}
