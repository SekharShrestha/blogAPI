package com.blog.dto;

//DTO obj are the actual data sent to client from server
//DTOs help hide the implementation details of jpa entities
//Exposing jpa entities through rest apis can be a security issue

public class JWTAuthResponse {
	
	private String accessToken;
	private String tokenType = "Bearer";
	
	public JWTAuthResponse(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
}
