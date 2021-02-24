package com.guikartman.rest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.guikartman.domain.entity.Usuario;
import com.guikartman.exception.SenhaInvalidaException;
import com.guikartman.rest.dto.CredenciaisDTO;
import com.guikartman.rest.dto.TokenDTO;
import com.guikartman.rest.dto.UsuarioDTO;
import com.guikartman.security.jwt.JwtService;
import com.guikartman.service.impl.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	private UsuarioServiceImpl usuarioService;
	private PasswordEncoder passwordEncoder;
	private JwtService jwtService;

	public UsuarioController(
			UsuarioServiceImpl usuarioService,
			PasswordEncoder passwordEncoder,
			JwtService jwtService) {
		this.usuarioService = usuarioService;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO salvar(@RequestBody @Valid Usuario usuario) {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return new UsuarioDTO(usuarioService.save(usuario));
	}
	
	@PostMapping("/auth")
	public TokenDTO autenticar(@RequestBody CredenciaisDTO credencias ) {
		try {
			Usuario usuario = new Usuario(null, credencias.getLogin(), credencias.getSenha(), false);
			UserDetails user = usuarioService.autenticar(usuario);
			String token = jwtService.gerarToken(usuario);
			return new TokenDTO(user.getUsername(), token);
		}catch (SenhaInvalidaException | UsernameNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}
}
