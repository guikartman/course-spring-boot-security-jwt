package com.guikartman.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.guikartman.exception.ObjectNotFoundException;
import com.guikartman.exception.RegraNegocioException;
import com.guikartman.rest.ApiErros;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(RegraNegocioException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErros handleRegraNegocioException(RegraNegocioException ex) {
		String mensagemErro = ex.getMessage();
		return new ApiErros(mensagemErro);
	}
	
	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErros handleObjectNotFoundException(ObjectNotFoundException ex) {
		String msgErro = ex.getMessage();
		return new ApiErros(msgErro);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErros handleMethodValidException( MethodArgumentNotValidException ex) {
		List<String> erros = ex.getBindingResult().getAllErrors()
					.stream()
					.map(erro -> erro.getDefaultMessage())
					.collect(Collectors.toList());
		return new ApiErros(erros);
	}
}
