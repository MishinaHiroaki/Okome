package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "orders")
public class Order {
	@Id //このフィールドがエンティティの識別子（主キー）であることを示します。
	@GeneratedValue(strategy = GenerationType.IDENTITY) //データベースの自動インクリメント機能に基づいて、主キーの値を自動的に生成する方法です。
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "user_id") 
    private User user;
	
	private int totalprice;
	private String status;
	private String stripe_session_id;
	
	@CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
	
    @OneToMany
    (mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;
}
