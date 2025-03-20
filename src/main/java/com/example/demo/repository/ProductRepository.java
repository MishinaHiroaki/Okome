package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Product;

//Repositoryインターフェース（具体的にはJpaRepository）を利用すると、データベースへのCRUD（Create, Read, Update, Delete）操作が簡単に実装できます
public interface ProductRepository extends JpaRepository<Product, Long> { //<エンティティのクラス,主キーの型>を受け取る

	// ページネーション対応 (すべての商品取得)
	Page<Product> findAll(Pageable pageable);

	//search0
	Page<Product> findByPrefectureId(int originId, Pageable pageable);
	//Product エンティティの origin_id に対応するのは Prefecture エンティティの id となります

	//search1
	Page<Product> findByVarietyId(int varietyId, Pageable pageable);

	//search2
	Page<Product> findByPriceBetween(int priceMin, int priceMax, Pageable pageable);

	Page<Product> findByMillingtypeIdAndPriceBetween(int millingtypeId, int priceMin, int priceMax, Pageable pageable);

	Page<Product> findByWeightIdAndPriceBetween(int weightId, int priceMin, int priceMax, Pageable pageable);

	Page<Product> findByMillingtypeIdAndWeightIdAndPriceBetween(int millingtypeId, int weightId, int priceMin,
			int priceMax, Pageable pageable);

	Product findById(int productId);
}
