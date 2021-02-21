package com.guikartman.service;

import java.util.Optional;

import com.guikartman.domain.entity.Pedido;
import com.guikartman.domain.enums.StatusPedido;
import com.guikartman.rest.dto.PedidoDTO;

public interface PedidoService {
	Pedido salvar(PedidoDTO dto);
	Optional<Pedido> obterPedidoCompleto(Integer id);
	void atualizaStatus(Integer id, StatusPedido status);
}
