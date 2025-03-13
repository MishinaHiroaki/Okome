package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.User;
import com.example.demo.repository.CartRepository;

@Service
public class CartService {
	
	private final CartRepository cartRepository;

	public CartService(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}
	
	public void cartSave(Cart cart) {
		cartRepository.save(cart);
	}
	
	public List<Cart> getAllCarts() {
		return cartRepository.findAll();
	}

	public void deleteById(Long cartId) {
		cartRepository.deleteById(cartId);
	
	}
	
	public Cart findByProductId(int productId) {
		return cartRepository.findByProductId(productId);
	}
	
	public List<Cart> getCartItemsByUser(User user) {
        return cartRepository.findByUserId(user.getId());
    }
	
	public Cart findByProductIdAndSessionId(int productId,String sessionId) {
		return cartRepository.findByProductIdAndSessionId(productId,sessionId);
	}
	
	
	
}
