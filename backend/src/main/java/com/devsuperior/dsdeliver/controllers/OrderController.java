package com.devsuperior.dsdeliver.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
	
	@Autowired
	private OrderService service;
	
	@GetMapping
	public ResponseEntity<List<OrderDTO>> findAll(){
		List<OrderDTO> list = service.findAll();
		return ResponseEntity.ok().body(list); 
	}
	
	@PostMapping
	public ResponseEntity<OrderDTO> insert (@RequestBody OrderDTO dto){ /* Body Json */
		dto = service.insert(dto);	/*** URI Código 201 de inserção ao banco de dados | recurso criado ***/
		URI uri  = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
		/* Criar new request no postman New Order
		 * | Validar no http://localhost:8080/h2-console 
		 * | Body row Json [] inserir objetos | Código 201 */
	}
	
	@PutMapping("/{id}/delivered")
	public ResponseEntity<OrderDTO> setDelivered(@PathVariable Long id){ 
		OrderDTO dto = service.setDelivered(id);
		return ResponseEntity.ok().body(dto); 
		/* Criar new request Set delivered 
		 * | PUT http://localhost:8080/orders/3/delivered 
		 * | postman Código 200 e validar no http://localhost:8080/h2-console 
		 * | Body row Json*/
	}
	
}
