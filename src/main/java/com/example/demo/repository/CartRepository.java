package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Long> {
	
	public List<Cart> findByProductId(int priductId);
	
}