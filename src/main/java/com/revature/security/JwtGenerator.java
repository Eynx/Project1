package com.revature.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expirationDate = new Date(currentDate.getTime() + 1000 * 60 * 60 * 24);
		return Jwts.builder().setSubject(username).setIssuedAt(currentDate).setExpiration(expirationDate).signWith(secretKey).compact();
	}

	public boolean validateToken(String token)
	{
		try
		{
			Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getUsernameFromToken(String token)
	{
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
	}
}
