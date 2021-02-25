package com.devsuperior.dsdeliver.services;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.dto.ProductDTO;
import com.devsuperior.dsdeliver.entities.Order;
import com.devsuperior.dsdeliver.entities.OrderStatus;
import com.devsuperior.dsdeliver.entities.Product;
import com.devsuperior.dsdeliver.repositories.OrderRepository;
import com.devsuperior.dsdeliver.repositories.ProductRepository;


@Service
public class OrderService {
	/*DTO data transfer objects recebe camada serviço*/
	
	/*injeção instanciar corretamente a dependencia*/
	
	@Autowired
	private OrderRepository  repository;
	
	@Autowired
	private ProductRepository  productRepository;
	
	public OrderService(OrderRepository repository) {
		this.repository = repository;
	}
	
	@Transactional(readOnly = true)  /*Spring garantia de conexão com o banco*/
	public List<OrderDTO> findAll()  {
		List<Order> list = repository.findOrdersWithProducts();/*do mais antigo ao mais recente pendente*/
		return list.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional /* Método para inserir novo pedido já associado com os produtos dele*/
	public OrderDTO insert(OrderDTO dto)  {
		Order order = new Order (null, dto.getAddress(), dto.getLatitude(),dto.getLongitude(),
		        Instant.now(), OrderStatus.PENDING); 
		for(ProductDTO p : dto.getProducts() ) {
			Product product = productRepository.getOne(p.getId());
			order.getProducts().add(product);
		}
		order = repository.save(order);
		return new OrderDTO(order);
	}
}

