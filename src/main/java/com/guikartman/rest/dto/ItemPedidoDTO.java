package com.guikartman.rest.dto;

import java.io.Serializable;

public class ItemPedidoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer produto;
	private Integer quantidade;
	
	public ItemPedidoDTO(Integer produto, Integer quatidade) {
		this.produto = produto;
		this.quantidade = quatidade;
	}

	public Integer getProduto() {
		return produto;
	}

	public void setProduto(Integer produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
