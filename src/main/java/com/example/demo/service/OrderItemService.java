package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.OrderItem;
import com.example.demo.repository.OrderItemRepository;

@Service
public class OrderItemService {
	private final OrderItemRepository orderItemRepository;
	
	public OrderItemService(OrderItemRepository orderItemRepository) {
		this.orderItemRepository = orderItemRepository;
	}

	public void OrderItemSave(OrderItem orderItem) {
		orderItemRepository.save(orderItem);
	} 
}
