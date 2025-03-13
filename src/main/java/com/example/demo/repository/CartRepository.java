package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Long> {
	
	public Cart findByProductId(int productId);
	
	 List<Cart> findByUserId(Long userId);

	public Cart findByProductIdAndSessionId(int productId, String sessionId);
	
	
}