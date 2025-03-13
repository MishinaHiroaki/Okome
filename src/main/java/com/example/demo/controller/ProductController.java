package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;

@Controller
public class ProductController {
	private final ProductService productService;
	private final CartService cartService;
	private final UserService userService;
	private final OrderService orderService;

	public ProductController(ProductService productService, CartService cartService,UserService userService,OrderService orderService) {
		this.productService = productService;
		this.cartService = cartService;
		this.userService = userService;
		this.orderService = orderService;
	}

	@GetMapping("/")
	public String topPage(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("productCount", products.size()); // ヒット件数を追加
		model.addAttribute("products", products);

		// 初期表示時の検索条件を設定
		model.addAttribute("millingtype", 0); // 全て
		model.addAttribute("weight", 0); // 全て
		model.addAttribute("price_min", 0); // 下限なし
		model.addAttribute("price_max", 1000000); // 上限なし
		return "Top";
	}

	@PostMapping("/search0")
	public String search0(@RequestParam int origin, Model model) {
		List<Product> products = productService.search0WithFilters(origin);
		model.addAttribute("productCount", products.size()); // ヒット件数を追加
		model.addAttribute("products", products);

		model.addAttribute("millingtype", 0); // 全て
		return "Top";
	}

	@PostMapping("/search1")
	public String search1(@RequestParam int variety, Model model) {
		List<Product> products = productService.search1WithFilters(variety);
		model.addAttribute("productCount", products.size()); // ヒット件数を追加
		model.addAttribute("products", products);

		model.addAttribute("millingtype", 0); // 全て
		return "Top";
	}

	@PostMapping("/search2")
	public String search2(@RequestParam int millingtype, @RequestParam int weight, @RequestParam int price_min,
			@RequestParam int price_max, Model model) {
		List<Product> products = productService.search2WithFilters(millingtype, weight, price_min, price_max);

		model.addAttribute("productCount", products.size()); // ヒット件数を追加
		model.addAttribute("products", products);

		// 検索条件をモデルに追加
		model.addAttribute("millingtype", millingtype);
		model.addAttribute("weight", weight);
		model.addAttribute("price_min", price_min);
		model.addAttribute("price_max", price_max);
		return "Top";
	}

	@PostMapping("/productDetail")
	public String showProductDetail(@RequestParam int productId, Model model) {
		// 商品情報を取得
		Product product = productService.getProductById(productId);
		Cart cart = cartService.findByProductId(productId);
		int quantity;
		if (cart != null) {
			quantity = cart.getQuantity();
		} else {
			// cart が null の場合の処理（例えば 0 をセットするなど）
			quantity = 0;
		}

		int zaiko = product.getStock_quantity() - quantity;

		model.addAttribute("product", product);

		model.addAttribute("zaiko", zaiko);

		return "ProductDetail";
	}

	@PostMapping("/cart")
	public String showCart(@RequestParam int productId, @RequestParam int quantity,
			RedirectAttributes redirectAttributes, HttpSession session) {

		 // セッションID取得 (未ログインユーザー用の識別子)
	    String sessionId = session.getId();
	    
		// 商品情報を取得
		Product product = productService.getProductById(productId);

		Cart existingCart = cartService.findByProductIdAndSessionId(productId, sessionId);
		if (existingCart != null) {
			// すでにカートにある場合 → 数量と合計金額を更新
			existingCart.setQuantity(existingCart.getQuantity() + quantity);
			existingCart.setTotalprice(existingCart.getPrice() * existingCart.getQuantity());
			cartService.cartSave(existingCart);
		} else {
			// カートに商品を保存
			Cart cart = new Cart();
			
			cart.setProduct(product);
			cart.setName(product.getName());
			cart.setImageurl(product.getImageurl());
			cart.setQuantity(quantity);
			cart.setPrice(product.getPrice());
			cart.setTotalprice(product.getPrice() * quantity);
			cart.setMillingtype(product.getMillingtype());
			cart.setPrefecture(product.getPrefecture());
			cart.setVariety(product.getVariety());
			cart.setWeight(product.getWeight());
			cart.setSessionId(sessionId);
			cartService.cartSave(cart);
		}

		List<Cart> carts = cartService.getAllCarts();
		int totalAmount = carts.stream().mapToInt(Cart::getTotalprice).sum();
		//carts.stream().mapToInt(cart -> cart.getTotalprice())と同じ

		// Flashスコープを使ってリダイレクト後もデータを保持
		redirectAttributes.addFlashAttribute("carts", carts);
		redirectAttributes.addFlashAttribute("totalAmount", totalAmount);
		// PRGパターンを適用（リダイレクトでGETリクエストに変換）
		return "redirect:/cart";
	}

	@GetMapping("/cart")
	public String getCart(Model model) {
		// カート情報を取得
		List<Cart> carts = cartService.getAllCarts();
		int totalAmount = carts.stream().mapToInt(Cart::getTotalprice).sum();

		model.addAttribute("carts", carts);
		model.addAttribute("totalAmount", totalAmount);

		return "Cart";
	}

	@PostMapping("/cartdelete")
	public String deleteCartItem(@RequestParam Long cartId) {
		cartService.deleteById(cartId); // cartId を元に削除
		System.out.println("cartId: " + cartId);
		return "redirect:/cart"; // 削除後、カート画面をリロード
	}

	@GetMapping("/order")
	public String order(Model model) {
		List<Cart> carts = cartService.getAllCarts();
		int totalAmount = carts.stream().mapToInt(Cart::getTotalprice).sum();

		model.addAttribute("carts", carts);
		model.addAttribute("totalAmount", totalAmount);
		return "Order1";
	}

	@PostMapping("/order2")
	public String order2(@RequestParam String name,
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam String postal_code,
			@RequestParam String address,
			@RequestParam String phone,
			Model model) {
		
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		List<Cart> carts = cartService.getAllCarts();
		int totalAmount = carts.stream().mapToInt(Cart::getTotalprice).sum();

		String encodeedPassword = bcpe.encode(password);// ハッシュ化
		
		model.addAttribute("carts", carts);
		model.addAttribute("totalAmount", totalAmount);

		model.addAttribute("name", name);
		model.addAttribute("email", email);
		model.addAttribute("postal_code", postal_code);
		model.addAttribute("address", address);
		model.addAttribute("phone", phone);
		model.addAttribute("password", encodeedPassword);

		return "Order2";
	}
	

	@PostMapping("/order3")
	public String order3(@RequestParam String name,
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam String postal_code,
			@RequestParam String address,
			@RequestParam String phone,
			@RequestParam int totalAmount,
			HttpSession session) {
		
		//1.既存ユーザか新規ユーザかどうか
		User existingUser = userService.findByEmail(email);
		User user = null;
		
		if (existingUser == null) { //新規ユーザの場合
			user = new User();
			user.setName(name);
			user.setEmail(email);
			user.setPostal_code(postal_code);
			user.setAddress(address);
			user.setPhone(phone);
			user.setPassword(password);
			
			//新規ユーザをデータベースに保存
			userService.saveUser(user);
		} else {
			user = existingUser;
		}
		//2.注文を作成
		Order order = new Order();
		order.setUser(user);
		order.setTotalprice(totalAmount);
		order.setStatus("pending"); 
		order.setCreatedAt(LocalDateTime.now());  
	    order.setUpdatedAt(LocalDateTime.now());
		
	    //3.注文情報をOrderテーブルに保存
	    orderService.orderSave(order);
	    
	    //4.カート内の商品を注文商品として保存(cartsテーブルのデータをorder_itemsテーブルへ渡す)
		

		return "Order3";
	}
	

}
