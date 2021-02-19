package com.guikartman;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.guikartman.domain.entity.Cliente;
import com.guikartman.domain.entity.Pedido;
import com.guikartman.domain.repository.ClienteRepository;
import com.guikartman.domain.repository.PedidoRepository;

@SpringBootApplication
public class CourseSpringBootSecurityApplication {
	
	@Bean
	public CommandLineRunner init(
			@Autowired ClienteRepository clientes, 
			@Autowired PedidoRepository pedidos) {
		return args -> {
			Cliente cliente = new Cliente(null, "Guilherme");
			clientes.save(cliente);
			
			Pedido p = new Pedido(null, cliente, LocalDate.now(), BigDecimal.valueOf(100));
			pedidos.save(p);
			
			Cliente c = clientes.findClienteFecthPedidos(cliente.getId());
			System.out.println(c);
			
			pedidos.findByCliente(cliente).forEach(System.out::println);
			
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CourseSpringBootSecurityApplication.class, args);
	}

}
