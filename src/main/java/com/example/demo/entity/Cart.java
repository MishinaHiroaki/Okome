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
@Table(name = "carts")
public class Cart {
	@Id//このフィールドがエンティティの識別子（主キー）であることを示します。
    @GeneratedValue(strategy = GenerationType.IDENTITY)//データベースの自動インクリメント機能に基づいて、主キーの値を自動的に生成する方法です。
    private Long id;
    private String name;
    private String imageurl;
    private int quantity;
    private int price;
    private int totalprice;
    
    @ManyToOne
    @JoinColumn(name = "product_id") // 外部キー
    private Product product; // Product エンティティと関連
    @ManyToOne
    @JoinColumn(name = "variety_id") // 外部キー列名に合わせて変更
    private Variety variety; // Varietyエンティティとの関連
    
    @ManyToOne
    @JoinColumn(name = "origin_id") // 外部キー列名に合わせて変更
    private Prefecture prefecture; // エンティティとの関連
    
    @ManyToOne
    @JoinColumn(name = "millingtype_id") // 外部キー列名に合わせて変更
    private Millingtype millingtype;
   
    @ManyToOne
    @JoinColumn(name = "weight_id") // 外部キー列名に合わせて変更
    private Weight weight;
}
