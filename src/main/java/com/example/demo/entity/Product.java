package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter//getter と setter メソッドを自動的に生成してくれるものです
@Setter
@Entity //そのクラスのインスタンス(Userクラスのこと)は、テーブルの行（レコード）としてデータベースに保存されます。
@Table(name = "products") //というテーブルに保存
public class Product {
    @Id//このフィールドがエンティティの識別子（主キー）であることを示します。
    private Long id;
    private String name;
    private String description;
    private String imageurl;
    private int price;
    private int stock_quantity;
    
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