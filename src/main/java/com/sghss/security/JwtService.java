package com.sghss.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "REMOVED"; // Base64 válida: 32 chars

	public String gerarToken(UserDetails userDetails) {
		return gerarToken(new HashMap<>(), userDetails);
	}

	public String gerarToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
				.signWith(getChaveAssinatura(), SignatureAlgorithm.HS256) // método novo exige chave + algoritmo
				.compact();
	}

	public String extrairEmail(String token) {
		return extrairClaim(token, Claims::getSubject);
	}

	public <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extrairTodosClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extrairTodosClaims(String token) {
		JwtParser parser = Jwts.parserBuilder().setSigningKey(getChaveAssinatura()).build();
		return parser.parseClaimsJws(token).getBody();
	}

	private Key getChaveAssinatura() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes); // chave compatível com HS256
	}

	public boolean tokenValido(String token, UserDetails userDetails) {
		final String email = extrairEmail(token);
		return (email.equals(userDetails.getUsername())) && !tokenExpirado(token);
	}

	private boolean tokenExpirado(String token) {
		return extrairExpiracao(token).before(new Date());
	}

	private Date extrairExpiracao(String token) {
		return extrairClaim(token, Claims::getExpiration);
	}
}
