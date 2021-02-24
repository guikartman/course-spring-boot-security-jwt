package com.guikartman.rest.dto;

import java.io.Serializable;

import com.guikartman.domain.entity.Usuario;

public class UsuarioDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String username;
	private boolean admin;
	
	public UsuarioDTO(Usuario usuario) {
		this.username = usuario.getUsername();
		this.admin = usuario.isAdmin();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
