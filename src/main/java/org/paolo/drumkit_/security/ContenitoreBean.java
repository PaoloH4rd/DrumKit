package org.paolo.drumkit_.security;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.repository.UtenteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

@Configuration
@RequiredArgsConstructor
public class ContenitoreBean {
	
	private final UtenteRepository repo;
	
	@Bean
	//implementazione di Userdetails
	protected UserDetailsService getDetailsService() {
		//
		return (u) -> repo.findByEmailAndIsDisattivatoIsFalse(u).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));	}
	
	@Bean
	//implementazione di PasswordEncoder
	protected PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	//implementazione di AuthenticationManager
	protected AuthenticationManager getManager
				(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}
	
	@Bean
	//implementazione di AuthenticationProvider
	protected AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider dap=new DaoAuthenticationProvider();
		dap.setUserDetailsService(getDetailsService());
		dap.setPasswordEncoder(getPasswordEncoder());
		return dap;
	}
}
