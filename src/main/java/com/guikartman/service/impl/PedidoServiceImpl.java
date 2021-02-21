package com.guikartman.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guikartman.domain.entity.Cliente;
import com.guikartman.domain.entity.ItemPedido;
import com.guikartman.domain.entity.Pedido;
import com.guikartman.domain.entity.Produto;
import com.guikartman.domain.enums.StatusPedido;
import com.guikartman.domain.repository.ClienteRepository;
import com.guikartman.domain.repository.ItemPedidoRepository;
import com.guikartman.domain.repository.PedidoRepository;
import com.guikartman.domain.repository.ProdutoRepository;
import com.guikartman.exception.ObjectNotFoundException;
import com.guikartman.exception.RegraNegocioException;
import com.guikartman.rest.dto.ItemPedidoDTO;
import com.guikartman.rest.dto.PedidoDTO;
import com.guikartman.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

	private PedidoRepository repository;
	private ClienteRepository clienteRepository;
	private ProdutoRepository produtoRepository;
	private ItemPedidoRepository itemPedidoRepository;

	public PedidoServiceImpl(
			PedidoRepository repository, 
			ClienteRepository clienteRepository,
			ProdutoRepository produtoRepository,
			ItemPedidoRepository itemPedidoRepository) {
		this.repository = repository;
		this.clienteRepository = clienteRepository;
		this.produtoRepository = produtoRepository;
		this.itemPedidoRepository = itemPedidoRepository;
	}

	@Override
	@Transactional
	public Pedido salvar(PedidoDTO dto) {
		Cliente cliente = clienteRepository
							.findById(dto.getCliente())
							.orElseThrow(() -> new RegraNegocioException("Código de cliente inválido: "+dto.getCliente()));
		
		Pedido pedido = new Pedido(null, cliente, LocalDate.now(), dto.getTotal(), StatusPedido.REALIZADO);
		
		List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
		
		repository.save(pedido);
		itemPedidoRepository.saveAll(itemsPedido);
		return pedido;
	}
	
	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		return repository.buscarPedidosComItens(id);
	}
	
	@Override
	@Transactional
	public void atualizaStatus(Integer id, StatusPedido status) {
		repository.findById(id)
				.map(p -> {
					p.setStatus(status);
					return repository.save(p);
				}).orElseThrow(() -> new ObjectNotFoundException("Pedido com o id "+id+" não foi encotrado"));
	}
	
	private List<ItemPedido> converterItems(Pedido pedido,List<ItemPedidoDTO> items) {
		if (items.isEmpty()) {
			throw new RegraNegocioException("Não é possivel realizar um pedido sem items.");
		}
		return items
				.stream()
				.map(dto -> {
					Produto produto = produtoRepository
										.findById(dto.getProduto())
										.orElseThrow(() -> new RegraNegocioException("Código de produto invalido: "+dto.getProduto()));
					
					return new ItemPedido(null, pedido, produto, dto.getQuantidade());
				}).collect(Collectors.toList());
	}
	
}
