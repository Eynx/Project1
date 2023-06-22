package com.revature.dtos;

public class AuthResponseDTO
{
	String accessToken;
	String tokenType = "Bearer ";

	public AuthResponseDTO()
	{
	}

	public AuthResponseDTO(String accessToken)
	{
		this.accessToken = accessToken;
	}

	public String getAccessToken()
	{
		return accessToken;
	}
	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}

	public String getTokenType()
	{
		return tokenType;
	}
	public void setTokenType(String tokenType)
	{
		this.tokenType = tokenType;
	}

	@Override
	public String toString()
	{
		return "AuthResponseDTO{" +
				"accessToken='" + accessToken + '\'' +
				", tokenType='" + tokenType + '\'' +
				'}';
	}
}
