package com.guikartman.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guikartman.domain.entity.Cliente;
import com.guikartman.domain.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

	List<Pedido> findByCliente(Cliente cliente);
}
