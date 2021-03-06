package com.devsuperior.dsdeliver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsdeliver.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	
	/* Docs .spring data JPA.io jpa query-methods OrderBY ordenado por nome*/
	
	List<Product> findAllByOrderByNameAsc();
	
}
