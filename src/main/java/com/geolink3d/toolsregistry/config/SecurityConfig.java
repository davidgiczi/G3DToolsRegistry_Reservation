package com.geolink3d.toolsregistry.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.geolink3d.toolsregistry.service.EncoderService;
import com.geolink3d.toolsregistry.service.GeoWorkerService;
import com.geolink3d.toolsregistry.service.GeoWorkerServiceImpl;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	private GeoWorkerService workerService;
	

	@Autowired
	public void setWorkerService(GeoWorkerService workerService) {
		this.workerService = workerService;
	}


	@Bean
	  public UserDetailsService userDetailsService() {
		 return new GeoWorkerServiceImpl();
	 }
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/tools-registry/login*").permitAll()
				.antMatchers("/tools-registry/registration").permitAll()
				.antMatchers("/script/**", "/css/**").permitAll()
				.antMatchers("/console/**").permitAll()
				.antMatchers("/tools-registry/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/tools-registry/login")
				.defaultSuccessUrl("/tools-registry/tools", true)
				.permitAll()
			.and()
			.logout()
			.logoutUrl("/logout")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.addLogoutHandler(new CustomLogoutHandler(workerService))
			.logoutSuccessUrl("/tools-registry/login?logout")
			.permitAll();
		
		http.headers().frameOptions().disable();
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     	
		auth.userDetailsService(userDetailsService()).passwordEncoder(new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				
				return rawPassword.equals(EncoderService.decodeByBase64(encodedPassword));
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				
				return EncoderService.encodeByBase64(String.valueOf(rawPassword));
			}
		});
       
    }
	

}
