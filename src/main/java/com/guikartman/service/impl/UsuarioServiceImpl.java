package com.guikartman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guikartman.domain.entity.Usuario;
import com.guikartman.domain.repository.UsuarioRepository;
import com.guikartman.exception.SenhaInvalidaException;

@Service
public class UsuarioServiceImpl implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UsuarioRepository repository;
	
	public UserDetails autenticar(Usuario usuario) {
		UserDetails user = loadUserByUsername(usuario.getUsername());
		boolean isEqual = encoder.matches(usuario.getSenha(), user.getPassword());
		if (isEqual) {
			return user;
		}
		throw new SenhaInvalidaException();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByUsername(username)
		.orElseThrow(() -> new UsernameNotFoundException("Usuario ou Senha incorreto."));
		
		String[] roles = usuario.isAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"};
		
		return User
				.builder()
				.username(usuario.getUsername())
				.password(usuario.getSenha())
				.roles(roles)
				.build();
	}
	
	@Transactional
	public Usuario save(Usuario usuario) {
		return repository.save(usuario);
	}

}
