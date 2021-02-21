package com.guikartman.rest;

import java.util.Arrays;
import java.util.List;

public class ApiErros {

	private List<String> erros;

	public ApiErros(String mensagemErro) {
		this.erros = Arrays.asList(mensagemErro);
	}
	
	public List<String> getErros() {
		return erros;
	}
}
