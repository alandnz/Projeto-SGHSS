package com.sghss.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("${jwt.secret}")
	private String secretKey;

	public String gerarToken(UserDetails userDetails) {
		return gerarToken(new HashMap<>(), userDetails);
	}

	public String gerarToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
				.signWith(getChaveAssinatura(), SignatureAlgorithm.HS256).compact();
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
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
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
