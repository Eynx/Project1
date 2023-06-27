package com.revature.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtGenerator
{
	private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	public String generateToken(Authentication authentication)
	{
		System.out.println(secretKey);
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expirationDate = new Date(currentDate.getTime() + 1000 * 60 * 60 * 24);
		return Jwts.builder().setSubject(username).setIssuedAt(currentDate).setExpiration(expirationDate).signWith(secretKey).compact();
	}

	public Jws<Claims> parseToken(String token)
	{
		try {
			return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
		} catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			return null;
		}
	}
}
