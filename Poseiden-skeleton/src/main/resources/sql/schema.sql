--
-- Table structure for table `bid`
--

DROP TABLE IF EXISTS `bid`;
CREATE TABLE `bid` (
  `id` Long NOT NULL AUTO_INCREMENT,
  `account` varchar(30) NOT NULL,
  `type` varchar(30) NOT NULL,
  `bid` double DEFAULT NULL,
  `bid_quantity` double DEFAULT NULL,
  `bid_date` datetime DEFAULT NULL,
  `ask` double DEFAULT NULL,
  `ask_quantity` double DEFAULT NULL,
  `benchmark` varchar(125) DEFAULT NULL,
  `commentary` varchar(125) DEFAULT NULL,
  `security` varchar(125) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `trader` varchar(125) DEFAULT NULL,
  `book` varchar(125) DEFAULT NULL,
  `side` varchar(125) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `creation_name` varchar(255) DEFAULT NULL,
  `deal_name` varchar(255) DEFAULT NULL,
  `deal_type` varchar(255) DEFAULT NULL,
  `revision_date` datetime DEFAULT NULL,
  `revision_name` varchar(255) DEFAULT NULL,
  `source_list_id` varchar(255) DEFAULT NULL,
  `bid_list_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `curvepoint`
--

DROP TABLE IF EXISTS `curvepoint`;
CREATE TABLE `curvepoint` (
  `id` Long NOT NULL AUTO_INCREMENT,
  `curve_id` Long DEFAULT NULL,
  `term` double DEFAULT NULL,
  `value` double DEFAULT NULL,
  `as_of_date` datetime DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
CREATE TABLE `rating` (
  `id` Long NOT NULL AUTO_INCREMENT,
  `fitch_rating` varchar(255) DEFAULT NULL,
  `moodys_rating` varchar(255) DEFAULT NULL,
  `sandp_rating` varchar(255) DEFAULT NULL,
  `order_number` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `rule`
--

DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule` (
  `id` Long NOT NULL AUTO_INCREMENT,
  `name` varchar(125) DEFAULT NULL,
  `description` varchar(125) DEFAULT NULL,
  `json` varchar(125) DEFAULT NULL,
  `template` varchar(512) DEFAULT NULL,
  `sql_part` varchar(255) DEFAULT NULL,
  `sql_str` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `trade`
--

DROP TABLE IF EXISTS `trade`;
CREATE TABLE `trade` (
  `id` Long NOT NULL AUTO_INCREMENT,
  `account` varchar(30) NOT NULL,
  `type` varchar(30) NOT NULL,
  `buy_quantity` double DEFAULT NULL,
  `sell_quantity` double DEFAULT NULL,
  `buy_price` double DEFAULT NULL,
  `sell_price` double DEFAULT NULL,
  `security` varchar(125) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `trader` varchar(125) DEFAULT NULL,
  `benchmark` varchar(125) DEFAULT NULL,
  `book` varchar(125) DEFAULT NULL,
  `side` varchar(125) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `creation_name` varchar(255) DEFAULT NULL,
  `deal_name` varchar(255) DEFAULT NULL,
  `deal_type` varchar(255) DEFAULT NULL,
  `revision_date` datetime DEFAULT NULL,
  `revision_name` varchar(255) DEFAULT NULL,
  `source_list_id` varchar(255) DEFAULT NULL,
  `trade_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` Long NOT NULL AUTO_INCREMENT,
  `username` varchar(125) DEFAULT NULL,
  `password` varchar(125) DEFAULT NULL,
  `fullname` varchar(125) DEFAULT NULL,
  `role` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Password is the same for both account : 123456789

INSERT users (username, password, fullname, role) VALUES
	 ('admin','$2y$10$1sVMRJP6/dMxMPe.NwM9HOc/erz2LRVRULZ7TuXhbbzrfI7zBxqIm','Administratrice','ADMIN'),
	 ('user','$2y$10$FU/xfV9rO3ktX/mL4YwKn.QtIBXWCzdcUTsMhDqZZ87EeEa/wArZO','Jane Doe','USER');