package com.guikartman.rest.dto;

import java.io.Serializable;

public class TokenDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String token;
	
	public TokenDTO() {
	}
	
	public TokenDTO(String login, String token) {
		super();
		this.login = login;
		this.token = token;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
