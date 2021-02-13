package com.devsuperior.dsdeliver.services;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.entities.Order;
import com.devsuperior.dsdeliver.repositories.OrderRepository;


@Service
public class OrderService {
	/*DTO data transfer objects recebe camada serviço*/
	
	/*injeção instanciar corretamente a dependencia*/
	
	@Autowired
	private OrderRepository repository;
	
	public OrderService(OrderRepository repository) {
		this.repository = repository;
	}
	
	@Transactional(readOnly = true)  /*Spring garantia de conexão com o banco*/
	public List<OrderDTO> findAll()  {
		List<Order> list = repository.findOrdersWithProducts();/*do mais antigo ao mais recente pendente*/
		return list.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());
	}
	
}

