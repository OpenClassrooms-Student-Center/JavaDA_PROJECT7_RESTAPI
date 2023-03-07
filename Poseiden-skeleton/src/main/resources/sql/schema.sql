--
-- Table structure for table `bid`
--

DROP TABLE IF EXISTS `bid`;
CREATE TABLE `bid` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
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
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `curve_id` int unsigned DEFAULT NULL,
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
  `id` int unsigned NOT NULL AUTO_INCREMENT,
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
  `id` int unsigned NOT NULL AUTO_INCREMENT,
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
  `id` int unsigned NOT NULL AUTO_INCREMENT,
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
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(125) DEFAULT NULL,
  `password` varchar(125) DEFAULT NULL,
  `fullname` varchar(125) DEFAULT NULL,
  `role` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Inserting two users
-- Password is the same for both account : Passw0rd-

INSERT users (username, password, fullname, role) VALUES
	 ('admin','$2a$10$ChL6CBbIlAIxCnSGmFOwWe1GdZgZpg.eGVOj7Wi23eJb.LOQFiN8C','Administratrice','ADMIN'),
	 ('user','$2a$10$d2xA9mXir5IfOAY7i3XbOeHxw1vPmn1WVxF7Tnd.aO2JXsLG89onC','Jane Doe','USER');