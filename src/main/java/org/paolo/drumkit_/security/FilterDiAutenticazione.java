package org.paolo.drumkit_.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FilterDiAutenticazione extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpSession session= request.getSession();
		String email=(String)session.getAttribute("email");

		String loginUrl = request.getContextPath() + "/login"; // URL della pagina di login
		String registerUrl = request.getContextPath() + "/register"; // URL della pagina di registrazione
		String welcomeUrl = request.getContextPath() + "/welcome"; // URL della pagina di registrazione
		String rootUrl = request.getContextPath() + "/"; // URL della pagina di registrazione
		String dashUrl = request.getContextPath() + "/dashboard"; // URL della pagina di registrazione

		// Controllo se la richiesta corrente è per la pagina di login
		if (
				request.getRequestURI().equals(loginUrl)
				|| request.getRequestURI().contains("all")
				|| request.getRequestURI().equals(registerUrl)
				|| request.getRequestURI().equals(rootUrl)
				|| request.getRequestURI().equals(welcomeUrl)
				|| request.getRequestURI().equals(dashUrl)
				) {
				//vai avanti con la richiesta
			filterChain.doFilter(request,response);
			//smetti di applicare il resto della pagina
			return;
		}
		//se sono in una pagina dove serve il controllo dell'utente
		//se non c'è un utente in sessione lo porta direttamente alla login
		// non lo blocca e setta lo user-details
		if (email == null ) {
			response.sendRedirect(loginUrl);
		} else {
			// Se l'utente è autenticato, setta lo user-details nel SecurityContext
			UserDetails user = userDetailsService.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(upat);
			filterChain.doFilter(request, response);
			System.out.println(user.getAuthorities());
		}

	}

}
