package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Products;
import com.example.demo.service.ProductService;

/*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // BCryptによるハッシュ化
import org.springframework.stereotype.Controller; // SpringのControllerアノテーション
import org.springframework.ui.Model; // Viewへデータを渡す
import org.springframework.web.bind.annotation.GetMapping; // GETリクエスト用のマッピング
import org.springframework.web.bind.annotation.ModelAttribute; // フォームデータの受け取り
import org.springframework.web.bind.annotation.PostMapping; // POSTリクエスト用のマッピング
import org.springframework.web.bind.annotation.RequestParam; // クエリパラメータの取得
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // リダイレクト時にデータを渡す
import jakarta.servlet.http.HttpSession; // セッション管理
import com.example.demo.entity.Users; // ユーザー情報を扱うエンティティ
import com.example.demo.service.UserService; // ユーザー関連のビジネスロジック
*/

@Controller
public class ProductController {
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/")
	public String topPage(Model model) {
		List<Products> products = productService.getAllProducts();
		model.addAttribute("productCount", products.size()); // ヒット件数を追加
		model.addAttribute("products", products);
		return "Top"; 
	}
	
	
	@PostMapping("/search0")
	public String search0(@RequestParam int origin,Model model) {
		List<Products> products = productService.search0WithFilters(origin);
		model.addAttribute("productCount", products.size()); // ヒット件数を追加
		model.addAttribute("products", products);
		return "Top"; 
	}
	
	@PostMapping("/search1")
	public String search1(@RequestParam int variety,Model model) {
		List<Products> products = productService.search1WithFilters(variety);
		model.addAttribute("productCount", products.size()); // ヒット件数を追加
		model.addAttribute("products", products);
		return "Top"; 
	}
	
	@PostMapping("/search2")
	public String search2(@RequestParam int millingtype,@RequestParam int weight,@RequestParam int price_min,@RequestParam int price_max, Model model) {
		List<Products> products = productService.search2WithFilters(millingtype,weight,price_min,price_max);
		
		model.addAttribute("productCount", products.size()); // ヒット件数を追加
		model.addAttribute("products", products);
		return "Top"; 
	}
	
}
