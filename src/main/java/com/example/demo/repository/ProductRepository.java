package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Product;

//Repositoryインターフェース（具体的にはJpaRepository）を利用すると、データベースへのCRUD（Create, Read, Update, Delete）操作が簡単に実装できます
public interface ProductRepository extends JpaRepository<Product, Long> { //<エンティティのクラス,主キーの型>を受け取る
	//search0
	public List<Product> findByPrefectureId(int originId);
	//Product エンティティの origin_id に対応するのは Prefecture エンティティの id となります
	
	//search1
	public List<Product> findByVarietyId(int varietyId);
	
	//search2
	public List<Product> findByPriceBetween(int priceMin, int priceMax);
	
	public List<Product> findByMillingtypeIdAndPriceBetween(int millingtypeId, int priceMin, int priceMax);
	
	public List<Product> findByWeightIdAndPriceBetween(int weightId, int priceMin, int priceMax);
	
	public List<Product> findByMillingtypeIdAndWeightIdAndPriceBetween(int millingtypeId, int weightId, int priceMin, int priceMax);
	
}
