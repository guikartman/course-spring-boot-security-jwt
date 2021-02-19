package com.guikartman.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guikartman.domain.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	
}
