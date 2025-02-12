package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter//getter と setter メソッドを自動的に生成してくれるものです
@Setter
@Entity //そのクラスのインスタンス(Userクラスのこと)は、テーブルの行（レコード）としてデータベースに保存されます。
@Table(name = "users") //usersというテーブルに保存
public class User {
    @Id//このフィールドがエンティティの識別子（主キー）であることを示します。
    @GeneratedValue(strategy = GenerationType.IDENTITY)//データベースの自動インクリメント機能に基づいて、主キーの値を自動的に生成する方法です。
    private Long id;
    private String email;
    private String name;
    private String password;
}