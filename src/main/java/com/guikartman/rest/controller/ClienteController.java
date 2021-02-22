package com.guikartman.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.guikartman.domain.entity.Cliente;
import com.guikartman.domain.repository.ClienteRepository;

@RestController
@RequestMapping(value = "/api/clientes")
public class ClienteController {
	
	private ClienteRepository repositorio;
	
	public ClienteController(ClienteRepository repositorio) {
		this.repositorio = repositorio;
	}
	
	@GetMapping("/{id}")
	public Cliente BuscarClientePorId(@PathVariable("id") Integer id ) {
		return this.repositorio
					.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não encontrado"));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save(@RequestBody @Valid Cliente cliente) {
		return this.repositorio.save(cliente);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		this.repositorio.findById(id)
					.map(cliente -> {
						repositorio.delete(cliente);
						return cliente;
					})
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update( @PathVariable Integer id, @RequestBody @Valid Cliente cliente) {
		this.repositorio
					.findById(id)
					.map(clienteExistente -> {
						cliente.setId(clienteExistente.getId());
						this.repositorio.save(cliente);
						return cliente;
					}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}
	
	@GetMapping
	public List<Cliente> find( Cliente filtro) {
		ExampleMatcher matcher = ExampleMatcher
										.matching()
										.withIgnoreCase()
										.withStringMatcher(StringMatcher.CONTAINING);
		return this.repositorio.findAll(Example.of(filtro, matcher));
	}
}
