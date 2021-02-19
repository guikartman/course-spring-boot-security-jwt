package com.guikartman.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.guikartman.domain.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	List<Cliente> findByNomeLike(String nome);
	
	boolean existsByNome(String nome);
	
	@Query(" select c from Cliente c left join fetch c.pedidos p where c.id = :id")
	Cliente findClienteFecthPedidos(@Param("id") Integer id);
	
}
