package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;


@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	// 商品をページネーションで取得するメソッド
	public Page<Product> getProductsWithPagination(int page, int size) {
		Pageable pageable = PageRequest.of(page, size); // ページサイズとページ番号を指定
		return productRepository.findAll(pageable); // ページネーションされた結果を返す
	}
	// 全商品をリストで取得 (ページネーションなし)
	public List<Product> getAllProducts() {
		return productRepository.findAll();//登録されたデータベースを返す
	}
	// originId による検索 (ページネーション対応)
	public Page<Product> search0WithFilters(int originId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productRepository.findByPrefectureId(originId, pageable);
	}
	// varietyId による検索 (ページネーション対応)
	public Page<Product> search1WithFilters(int varietyId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productRepository.findByVarietyId(varietyId, pageable);
	}

	public Product getProductById(int productId) {
		return productRepository.findById(productId);
	}

	public Page<Product> search2WithFilters(int millingtypeId, int weightId, int priceMin, int priceMax, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		if (millingtypeId == 0) {
			if (weightId == 0) {
				return productRepository.findByPriceBetween(priceMin, priceMax, pageable);
			} else {
				return productRepository.findByWeightIdAndPriceBetween(weightId, priceMin, priceMax, pageable);
			}

		} else {
			if (weightId == 0) {
				return productRepository.findByMillingtypeIdAndPriceBetween(millingtypeId, priceMin, priceMax, pageable);
			}
			return productRepository.findByMillingtypeIdAndWeightIdAndPriceBetween(millingtypeId, weightId, priceMin,
					priceMax, pageable);
		}
	}

	public Page<Product> getProducts(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productRepository.findAll(pageable);
	}

}
