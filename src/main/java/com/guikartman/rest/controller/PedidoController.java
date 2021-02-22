package com.guikartman.rest.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.guikartman.domain.entity.ItemPedido;
import com.guikartman.domain.entity.Pedido;
import com.guikartman.domain.enums.StatusPedido;
import com.guikartman.rest.dto.AtualizaStatusPedidoDTO;
import com.guikartman.rest.dto.InformacoesItemPedidoDTO;
import com.guikartman.rest.dto.InformacoesPedidoDTO;
import com.guikartman.rest.dto.PedidoDTO;
import com.guikartman.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private PedidoService service;

	public PedidoController(PedidoService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public Integer save(@RequestBody @Valid PedidoDTO dto) {
		Pedido pedido = service.salvar(dto);
		return pedido.getId();
	}

	@GetMapping("/{id}")
	public InformacoesPedidoDTO recuperarPorId(@PathVariable Integer id) {
		return service
				.obterPedidoCompleto(id)
				.map(p -> converter(p))
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
	}
	
	@PatchMapping("/{id}")
	@ResponseStatus(NO_CONTENT)
	public void updateStatus(@PathVariable(name = "id") Integer id, @RequestBody AtualizaStatusPedidoDTO dto) {
		service.atualizaStatus(id, StatusPedido.valueOf(dto.getStatus()));
	}
	
	private InformacoesPedidoDTO converter(Pedido pedido) {
		return new InformacoesPedidoDTO(pedido.getId(), pedido.getCliente().getCpf(), pedido.getCliente().getNome(),
				pedido.getDataPedido(),pedido.getTotal(), pedido.getStatus().name(),converterItemPedido(pedido.getItens()));
	}
	
	private List<InformacoesItemPedidoDTO> converterItemPedido(Set<ItemPedido> itens) {
		if (CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		
		return itens.stream()
					.map(p -> {
						return new InformacoesItemPedidoDTO(
								p.getProduto().getDescricao(), 
								p.getProduto().getPrecoUnitario(),
								p.getQuantidade());
						}).collect(Collectors.toList());
	}
}
