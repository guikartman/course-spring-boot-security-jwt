package com.guikartman.security.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.guikartman.CourseSpringBootSecurityApplication;
import com.guikartman.domain.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Value("${secutiry.jwt.expiracao}")
	private String expiracao;
	
	@Value("${secutiry.jwt.chave-assinatura}")
	private String chaveAssinatura;
	
	public String gerarToken(Usuario usuario) {
		Long expStr = Long.valueOf(expiracao);
		LocalDateTime exp = LocalDateTime.now().plusMinutes(expStr);
		Instant instant = exp.atZone(ZoneId.systemDefault()).toInstant();
		Date data = Date.from(instant);
		
		return Jwts.builder()
				.setSubject(usuario.getUsername())
				.setExpiration(data)
				.signWith(SignatureAlgorithm.HS512, chaveAssinatura)
				.compact();
	}
	
	private Claims obterClaims(String token) throws ExpiredJwtException {
		return Jwts
				.parser()
				.setSigningKey(chaveAssinatura)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public boolean tokenValido( String token ) {
		try {
			Claims claims = obterClaims(token);
			Date dataExpiracao = claims.getExpiration();
			LocalDateTime exp = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			return !LocalDateTime.now().isAfter(exp);
		}catch (Exception e) {
			return false;
		}
	}
	
	public String obterLoginUsuario(String token) throws ExpiredJwtException {
		return (String) obterClaims(token).getSubject();
	}
	
	public static void main(String args[]) {
		ApplicationContext contex = SpringApplication.run(CourseSpringBootSecurityApplication.class);
		JwtService jwtService = contex.getBean(JwtService.class);
		Usuario usuario = new Usuario();
		usuario.setUsername("kartman");
		String token = jwtService.gerarToken(usuario);
		System.out.println(token);
		
		boolean valido = jwtService.tokenValido(token);
		System.out.println("O token está válido? "+valido);
		
		System.out.println(jwtService.obterLoginUsuario(token));
	}
}
