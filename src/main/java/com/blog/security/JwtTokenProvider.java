package com.blog.security;


import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.blog.exceptions.BlogApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationInMs;
	
	//generate tokens
	public String generateToken(Authentication authentication) {
		String userName = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);
		
		String token = Jwts.builder()
					       .setSubject(userName)
					       .setIssuedAt(new Date())
					       .setExpiration(expireDate)
					       .signWith(SignatureAlgorithm.HS512, jwtSecret)
					       .compact();
		return token;
	}
	
	//get userName from token
	public String getUserNameFromJWT(String token) {
		Claims claims = Jwts.parser()
						    .setSigningKey(jwtSecret)
						    .parseClaimsJws(token)
						    .getBody();
		return claims.getSubject();
	}
	
	//validate JWT token
	public boolean validateToken(String token) throws BlogApiException {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		}
		catch(SignatureException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Signature");
		}
		catch(MalformedJwtException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
		}
		catch(ExpiredJwtException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
		}
		catch(IllegalArgumentException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "String is empty");
		}
		
	}

}
