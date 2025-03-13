package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	public void orderSave(Order order) {
		orderRepository.save(order);
	}
	

}
