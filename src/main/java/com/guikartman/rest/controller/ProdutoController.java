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

import com.guikartman.domain.entity.Produto;
import com.guikartman.domain.repository.ProdutoRepository;

@RestController
@RequestMapping(value = "/api/produtos")
public class ProdutoController {
	
	private ProdutoRepository repositorio;
	
	public ProdutoController(ProdutoRepository repositorio) {
		this.repositorio = repositorio;
	}
	
	@GetMapping("/{id}")
	public Produto BuscarProdutoPorId(@PathVariable("id") Integer id ) {
		return this.repositorio
					.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto save(@RequestBody @Valid Produto produto) {
		return this.repositorio.save(produto);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		this.repositorio.findById(id)
					.map(produto -> {
						repositorio.delete(produto);
						return null;
					})
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update( @PathVariable Integer id, @RequestBody @Valid Produto produto) {
		repositorio.findById(id)
					.map(p -> {
						produto.setId(p.getId());
						return repositorio.save(produto);
					}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}
	
	@GetMapping
	public List<Produto> find( Produto filtro) {
		ExampleMatcher matcher = ExampleMatcher
										.matching()
										.withIgnoreCase()
										.withStringMatcher(StringMatcher.CONTAINING);
		return repositorio.findAll(Example.of(filtro, matcher));
	}
}
