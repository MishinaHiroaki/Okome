package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();//登録されたデータベースを返す
	}

	public List<Product> search0WithFilters(int originId){
		return productRepository.findByPrefectureId(originId);
	}
	
	public List<Product> search1WithFilters(int varietyId){
		return productRepository.findByVarietyId(varietyId);
	}
	
	public Product getProductById(int productId) {
		return productRepository.findById(productId);
	}

	public List<Product> search2WithFilters(int millingtypeId, int weightId, int priceMin, int priceMax) {
		if (millingtypeId == 0) {
			if (weightId == 0) {
				return productRepository.findByPriceBetween(priceMin, priceMax);
			} else {
				return productRepository.findByWeightIdAndPriceBetween(weightId, priceMin, priceMax);
			}

		} else {
			if (weightId == 0) {
				return productRepository.findByMillingtypeIdAndPriceBetween(millingtypeId, priceMin, priceMax);
			}
			return productRepository.findByMillingtypeIdAndWeightIdAndPriceBetween(millingtypeId, weightId, priceMin, priceMax);
		}
	}

}
