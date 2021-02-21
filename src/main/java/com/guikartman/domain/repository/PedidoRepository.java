package com.guikartman.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.guikartman.domain.entity.Cliente;
import com.guikartman.domain.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

	List<Pedido> findByCliente(Cliente cliente);
	
	@Query("select p from Pedido p left join fetch p.itens where p.id = :id")
	Optional<Pedido> buscarPedidosComItens(@Param("id") Integer id);
}
