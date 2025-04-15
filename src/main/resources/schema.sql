SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS orderitems;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS varieties;
DROP TABLE IF EXISTS millingtypes;
DROP TABLE IF EXISTS prefectures;
DROP TABLE IF EXISTS weights;

DROP TABLE IF EXISTS carts;
SET FOREIGN_KEY_CHECKS=1;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  `postal_code` VARCHAR(20)  NOT NULL,
  `address` TEXT NOT NULL,
  `phone` VARCHAR(20)  NOT NULL,
  `stripe_customer_id` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `varieties` (
  `id` bigint NOT NULL,
  `variety` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `millingtypes` (
  `id` bigint NOT NULL,
  `millingtype` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ;

CREATE TABLE `prefectures` (
  `id` bigint NOT NULL,
  `prefecture` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `weights` (
  `id` bigint NOT NULL,
  `weight` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE products (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) DEFAULT NULL,
  origin_id BIGINT DEFAULT NULL,
  variety_id BIGINT DEFAULT NULL,
  millingtype_id BIGINT DEFAULT NULL,
  weight_id BIGINT DEFAULT NULL,
  price INT NOT NULL,
  stock_quantity INT NOT NULL,
  created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  imageurl VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK6ec48uky801uyr3hj91v8kvyv (variety_id),
  KEY FK7h6mhurr7lvmrm6yu54ws6axe (origin_id),
  KEY FK7rkyavsqcsv6d39cksqyvmgiu (millingtype_id),
  KEY FK81je30putk4g0bknxb43qwjsg (weight_id),
  CONSTRAINT FK6ec48uky801uyr3hj91v8kvyv FOREIGN KEY (variety_id) REFERENCES varieties (id),
  CONSTRAINT FK7h6mhurr7lvmrm6yu54ws6axe FOREIGN KEY (origin_id) REFERENCES prefectures (id),
  CONSTRAINT FK7rkyavsqcsv6d39cksqyvmgiu FOREIGN KEY (millingtype_id) REFERENCES millingtypes (id),
  CONSTRAINT FK81je30putk4g0bknxb43qwjsg FOREIGN KEY (weight_id) REFERENCES weights (id)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `carts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT DEFAULT NULL,
  `session_id` VARCHAR(255) DEFAULT NULL,
  `product_id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `quantity` INT NOT NULL,
  `price` INT NOT NULL,
  `totalprice` INT NOT NULL,
  `origin_id` BIGINT NOT NULL,
  `variety_id` BIGINT NOT NULL,
  `millingtype_id` BIGINT NOT NULL,
  `weight_id` BIGINT NOT NULL,
  `imageurl` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`),

  --  インデックスを有効化
  KEY `FK_carts_product` (`product_id`),
  KEY `FK_carts_variety` (`variety_id`),
  KEY `FK_carts_origin` (`origin_id`),
  KEY `FK_carts_millingtype` (`millingtype_id`),
  KEY `FK_carts_weight` (`weight_id`),
  KEY `FK_carts_user_id` (`user_id`),

  --  外部キー制約（統一 + `ON DELETE CASCADE` を追加）
  CONSTRAINT `FK_carts_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_carts_variety` FOREIGN KEY (`variety_id`) REFERENCES `varieties` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_carts_origin` FOREIGN KEY (`origin_id`) REFERENCES `prefectures` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_carts_millingtype` FOREIGN KEY (`millingtype_id`) REFERENCES `millingtypes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_carts_weight` FOREIGN KEY (`weight_id`) REFERENCES `weights` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_carts_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `orders`(
`id` INT NOT NULL AUTO_INCREMENT,
`user_id` INT NOT NULL,
`totalprice` INT NOT NULL,
`status` varchar(50) NOT NULL,
`stripe_session_id` varchar(255) DEFAULT NULL,
`created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
`updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (`id`),
--  外部キー制約
  CONSTRAINT `FK_orders_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

CREATE TABLE `orderitems` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,          --  注文ごとの識別ID
  `product_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `price` INT NOT NULL,
  `totalprice` INT NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  PRIMARY KEY (`id`),
  --  インデックスを有効化
  KEY `FK_orderitems_order` (`order_id`),
  KEY `FK_orderitems_product` (`product_id`),
  
  --  外部キー制約
  CONSTRAINT `FK_orderitems_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_orderitems_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE
 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

