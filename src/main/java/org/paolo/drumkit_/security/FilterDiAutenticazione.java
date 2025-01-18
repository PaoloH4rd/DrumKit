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
		String rootUrl = request.getContextPath() + "/"; // URL della pagina di registrazione
		String loginUrl = request.getContextPath() + "/login"; // URL della pagina di login


		// Controllo se la richiesta corrente è per la pagina di login
		if (
				request.getRequestURI().contains("login")
				|| request.getRequestURI().contains("all")
				|| request.getRequestURI().contains("register")
				|| request.getRequestURI().equals(rootUrl)
				|| request.getRequestURI().contains("welcome")
				|| request.getRequestURI().contains("favicon")
				|| request.getRequestURI().contains(".css")
				|| request.getRequestURI().contains(".js")
				|| request.getRequestURI().contains("/immagine")
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
			response.sendRedirect(request.getContextPath() + "/login?notLogged=true");
		} else {
			// Se l'utente è autenticato, setta lo user-details nel SecurityContext
			UserDetails user = userDetailsService.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(upat);
			filterChain.doFilter(request, response);
		}

	}

}
