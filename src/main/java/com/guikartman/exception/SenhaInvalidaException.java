package com.guikartman.exception;

public class SenhaInvalidaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SenhaInvalidaException() {
		super("Usuário ou Senha incorreta.");
	}
}
