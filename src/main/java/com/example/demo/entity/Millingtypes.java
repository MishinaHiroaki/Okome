package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter //getter と setter メソッドを自動的に生成してくれるものです
@Setter
@Entity //そのクラスのインスタンス(Userクラスのこと)は、テーブルの行（レコード）としてデータベースに保存されます。
@Table(name = "millingtypes") //millingtypesというテーブルに保存
public class Millingtypes {
	@Id //このフィールドがエンティティの識別子（主キー）であることを示します。
	private Long id;
	private String millingtype;
}
