package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Products;

//Repositoryインターフェース（具体的にはJpaRepository）を利用すると、データベースへのCRUD（Create, Read, Update, Delete）操作が簡単に実装できます
public interface ProductRepository extends JpaRepository<Products, Long> { //<エンティティのクラス,主キーの型>を受け取る
	//search0
	public List<Products> findByPrefectureId(int originId);
	//Products エンティティの origin_id に対応するのは Prefectures エンティティの id となります
	
	//search1
	public List<Products> findByVarietyId(int varietyId);
	
	//search2
	public List<Products> findByPriceBetween(int priceMin, int priceMax);
	
	public List<Products> findByMillingtypeIdAndPriceBetween(int millingtypeId, int priceMin, int priceMax);
	
	public List<Products> findByWeightIdAndPriceBetween(int weightId, int priceMin, int priceMax);
	
	public List<Products> findByMillingtypeIdAndWeightIdAndPriceBetween(int millingtypeId, int weightId, int priceMin, int priceMax);
	
}
