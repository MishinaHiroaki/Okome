package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orderitems")
public class OrderItem {
	@Id //このフィールドがエンティティの識別子（主キー）であることを示します。
	@GeneratedValue(strategy = GenerationType.IDENTITY) //データベースの自動インクリメント機能に基づいて、主キーの値を自動的に生成する方法です。
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id") // 外部キー
	private Product product; // Product エンティティと関連

	@ManyToOne
	@JoinColumn(name = "order_id") // 外部キー: Order (注文)
	private Order order; // Order エンティティと関連

	private int quantity;
	private int price;
	private int totalprice;
}
