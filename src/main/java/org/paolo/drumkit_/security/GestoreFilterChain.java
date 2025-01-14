package org.paolo.drumkit_.security;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.model.Ruolo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class GestoreFilterChain {
	
	private final FilterDiAutenticazione filter;
	private final AuthenticationProvider provider;
	@Bean
	protected SecurityFilterChain getFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(req->req
					.requestMatchers("/pannelloAdmin/**").hasAnyRole(Ruolo.ADMIN.toString(), Ruolo.SUPER_ADMIN.toString())
					.requestMatchers("/pannelloSuperAdmin/**").hasRole(Ruolo.SUPER_ADMIN.toString())
					.requestMatchers("/dashboard/**").authenticated()
					.anyRequest().permitAll()
					)
			.authenticationProvider(provider)
			.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
