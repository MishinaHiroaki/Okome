package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;

@Controller
public class ProductController {
	private final ProductService productService;
	private final CartService cartService;
	private final UserService userService;
	private final OrderService orderService;
	private final OrderItemService orderItemService;

	public ProductController(ProductService productService, CartService cartService, UserService userService,
			OrderService orderService, OrderItemService orderItemService) {
		this.productService = productService;
		this.cartService = cartService;
		this.userService = userService;
		this.orderService = orderService;
		this.orderItemService = orderItemService;
	}

	@GetMapping("/login")
	public String Login() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // これでセッション情報を完全削除
		return "redirect:/";
	}

	@PostMapping("/loginA")
	public String LoginA(@RequestParam String email,
			@RequestParam String password,
			HttpSession session,
			RedirectAttributes redirectAttributes,
			Model model) {
		User user = userService.findByEmail(email);
		if (user == null) {
			model.addAttribute("error","そのメールアドレスは登録されていません");
			return "login";
		}
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		
		if (!bcpe.matches(password,user.getPassword())) {
			model.addAttribute("error2","パスワードが間違っています");
			return "login";
		}
		
		session.setAttribute("userName", user.getName());
		return "redirect:/";
	}

	@GetMapping("/")
	public String topPage(Model model,
			HttpServletRequest request,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		// 商品リストをページネーションして取得
		Page<Product> productPage = productService.getProducts(page, size);

		model.addAttribute("productCount", productPage.getTotalElements()); // 総件数
		model.addAttribute("products", productPage.getContent()); // 商品リスト
		model.addAttribute("currentPage", page); // 現在のページ
		model.addAttribute("totalPages", productPage.getTotalPages()); // 総ページ数
		model.addAttribute("pageSize", size);

		// 初期表示時の検索条件を設定
		model.addAttribute("millingtype", 0); // 全て
		model.addAttribute("weight", 0); // 全て
		model.addAttribute("price_min", 0); // 下限なし
		model.addAttribute("price_max", 1000000); // 上限なし

		System.out.println("page=" + page);
		System.out.println("size=" + size);
		model.addAttribute("requestURI", request.getRequestURI());
		System.out.println(request.getRequestURI());
		return "Top";
	}

	@GetMapping("/search0")
	public String search0(@RequestParam int origin,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			HttpServletRequest request,
			Model model) {
		Page<Product> products = productService.search0WithFilters(origin, page, size);
		// ページネーションされたデータをモデルに追加
		model.addAttribute("productCount", products.getTotalElements()); // 総件数
		model.addAttribute("products", products.getContent()); // 商品リスト
		model.addAttribute("currentPage", page); // 現在のページ
		model.addAttribute("totalPages", products.getTotalPages()); // 総ページ数
		model.addAttribute("pageSize", size); // 1ページの件数
		model.addAttribute("origin", origin); // ← 産地を渡す
		model.addAttribute("millingtype", 0); // 全て
		model.addAttribute("requestURI", request.getRequestURI());
		System.out.println(request.getRequestURI());

		return "Top";
	}

	@GetMapping("/search1")
	public String search1(@RequestParam int variety,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			HttpServletRequest request,
			Model model) {
		Page<Product> products = productService.search1WithFilters(variety, page, size);

		// ページネーションされたデータをモデルに追加
		model.addAttribute("productCount", products.getTotalElements()); // 総件数
		model.addAttribute("products", products.getContent()); // 商品リスト
		model.addAttribute("currentPage", page); // 現在のページ
		model.addAttribute("totalPages", products.getTotalPages()); // 総ページ数
		model.addAttribute("pageSize", size); // 1ページの件数
		model.addAttribute("variety", variety); // ← 品種を渡す
		model.addAttribute("millingtype", 0); // 全て
		model.addAttribute("requestURI", request.getRequestURI());
		System.out.println(request.getRequestURI());
		return "Top";
	}

	@GetMapping("/search2")
	public String search2(@RequestParam int millingtype,
			@RequestParam int weight,
			@RequestParam int price_min,
			@RequestParam int price_max,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			HttpServletRequest request,
			Model model) {
		Page<Product> products = productService.search2WithFilters(millingtype, weight, price_min, price_max, page,
				size);

		// ページネーションされたデータをモデルに追加
		model.addAttribute("productCount", products.getTotalElements()); // 総件数
		model.addAttribute("products", products.getContent()); // 商品リスト
		model.addAttribute("currentPage", page); // 現在のページ
		model.addAttribute("totalPages", products.getTotalPages()); // 総ページ数
		model.addAttribute("pageSize", size); // 1ページの件数
		model.addAttribute("requestURI", request.getRequestURI());
		System.out.println(request.getRequestURI());

		// 検索条件をモデルに追加
		model.addAttribute("millingtype", millingtype);
		model.addAttribute("weight", weight);
		model.addAttribute("price_min", price_min);
		model.addAttribute("price_max", price_max);

		return "Top";
	}

	@PostMapping("/productDetail")
	public String showProductDetail(@RequestParam int productId, Model model, HttpSession session) {
		// 商品情報を取得
		Product product = productService.getProductById(productId);
		String sessionId = session.getId(); // セッションID取得
		Cart cart = cartService.findByProductIdAndSessionId(productId, sessionId);
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
		String sessionId = session.getId(); // セッションID取得
		System.out.println("【カート追加時のセッションID】" + sessionId);
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
	public String getCart(Model model, HttpSession session) {
		String sessionId = session.getId(); // セッションID取得
		// カート情報を取得
		List<Cart> carts = cartService.findBySessionId(sessionId);
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
	public String order(Model model, HttpSession session) {
		String sessionId = session.getId(); // セッションID取得
		List<Cart> carts = cartService.findBySessionId(sessionId);
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
			Model model, HttpSession session) {

		String sessionId = session.getId(); // セッションID取得

		// メールアドレスが既に登録されているか確認
		if (userService.findByEmail(email) != null) {
			List<Cart> carts = cartService.findBySessionId(sessionId);
			int totalAmount = carts.stream().mapToInt(Cart::getTotalprice).sum();
			model.addAttribute("carts", carts);
			model.addAttribute("totalAmount", totalAmount);
			model.addAttribute("error", "このメールアドレスは既に登録されています。ログインしてください。");
			return "Order1";
		}

		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		List<Cart> carts = cartService.findBySessionId(sessionId);
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
			Model model,
			HttpSession session) {

		String sessionId = session.getId(); // 再度セッションIDを取得
		System.out.println("【注文時のセッションID】" + sessionId);

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
		List<Cart> cartItems = cartService.findBySessionId(sessionId);

		for (Cart cartItem : cartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPrice(cartItem.getPrice());
			orderItem.setTotalprice(cartItem.getTotalprice());

			orderItemService.OrderItemSave(orderItem);//cartsテーブルのデータをorder_itemsテーブルへ渡す

			cartService.removeItemFromCart(cartItem);//order_itemsテーブルへ渡したcartsテーブルのデータを削除する
		}
		model.addAttribute("name", name);
		return "Order3";
	}

	//	@GetMapping("/first_time")
	//	public String first_time() {
	//		return "first_time";
	//	}

}
