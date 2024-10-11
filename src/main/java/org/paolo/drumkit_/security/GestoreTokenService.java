package org.paolo.drumkit_.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.paolo.drumkit_.model.Ruolo;
import org.paolo.drumkit_.model.Utente;
import org.paolo.drumkit_.service.def.UtenteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class GestoreTokenService {
	
	private final UtenteService service;
	
	@Value("${mia.chiave.per.jwt}")
	private String testoPerLaChiave;
	
	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(testoPerLaChiave.getBytes());
	}
	
	public String creaToken(Utente u) {
		//1000L sono millisecondi
		//*60*60*24 sono un giorno
		//*10 sono dieci giorni
		long dieciGiorni=1000L*60*60*24*10;
		return Jwts
				//apro il builder per creare il mio token
					.builder()
					//inizio ad inserire i dati nel payload
						.claims()
						//setto tutti i dati
							.subject(u.getEmail())
							.issuedAt(new Date(System.currentTimeMillis()))
							.expiration(new Date(System.currentTimeMillis()+dieciGiorni))
							.add("ruolo",u.getRuolo())
							//ho finito di inserire i dati
						.and()
						//firmo il token con la mia chiave segreta
					.signWith(getSecretKey())
					//creo finalmente il token
				.compact();				
	}
	
	private Claims getClaims(String token) {
		if(token==null)throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		if(token.startsWith("Bearer ")) {
			token=token.substring(7);
		}
		try {
			Claims c=Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
				return c;
		}catch (JwtException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,e.getMessage());
		}
		
	}
	
	public Date getCreationDate(String token) {
		return getClaims(token).getIssuedAt();
	}
	
	public Ruolo getRuolo(String token) {
		return (Ruolo)getClaims(token).get("ruolo");
	}
	
	public LocalDateTime getExpiration(String token) {
		Date d=getClaims(token).getExpiration();
		return LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
	}
	
	public Utente getUtente(String token) {
		String email=getClaims(token).getSubject();
		Utente u=service.getByEmail(email);
		return u;
	}

}
