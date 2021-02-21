package com.guikartman.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer cliente;
	private BigDecimal total;
	private List<ItemPedidoDTO> items;
	
	public PedidoDTO(Integer cliente, BigDecimal total, List<ItemPedidoDTO> items) {
		super();
		this.cliente = cliente;
		this.total = total;
		this.items = items;
	}

	public Integer getCliente() {
		return cliente;
	}
	
	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public List<ItemPedidoDTO> getItems() {
		return items;
	}
	
	public void setItems(List<ItemPedidoDTO> items) {
		this.items = items;
	}
}
