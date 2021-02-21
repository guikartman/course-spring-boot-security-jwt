package com.guikartman.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.guikartman.domain.enums.StatusPedido;

public class InformacoesPedidoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private  Integer codigo;
	private String cpf;
	private String nomeCliente;
	private LocalDate dataPedido;
	private BigDecimal total;
	private String status;
	private List<InformacoesItemPedidoDTO> items;
	
	public InformacoesPedidoDTO() {
	}
	
	public InformacoesPedidoDTO(
			Integer codigo, 
			String cpf, 
			String nomeCliente,
			LocalDate dataPedido,
			BigDecimal total,
			String status,
			List<InformacoesItemPedidoDTO> items) {
		this.codigo = codigo;
		this.cpf = cpf;
		this.nomeCliente = nomeCliente;
		this.dataPedido = dataPedido;
		this.total = total;
		this.status = status;
		this.items = items;
	}

	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public LocalDate getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<InformacoesItemPedidoDTO> getItems() {
		return items;
	}
	public void setItems(List<InformacoesItemPedidoDTO> items) {
		this.items = items;
	}
}
