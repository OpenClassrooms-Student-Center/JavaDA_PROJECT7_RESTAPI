SHOW DATABASES;
CREATE DATABASE IF NOT EXISTS demo;
USE demo;

-- Table bidlist
DROP TABLE IF EXISTS bidlist;
CREATE TABLE bidlist (
  bid_list_id int NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bid_quantity DOUBLE,
  ask_quantity DOUBLE,
  bid DOUBLE,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bid_list_date TIMESTAMP NULL,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP NULL,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP NULL,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),
  PRIMARY KEY (bid_list_id)
);

-- Table trade
DROP TABLE IF EXISTS trade;
CREATE TABLE trade (
  trade_id int NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buy_quantity DOUBLE,
  sell_quantity DOUBLE,
  buy_price DOUBLE,
  sell_price DOUBLE,
  trade_date TIMESTAMP NULL,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP NULL,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP NULL,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),
  PRIMARY KEY (trade_id)
);

-- Table curvepoint
DROP TABLE IF EXISTS curvepoint;
CREATE TABLE curvepoint (
  id int NOT NULL AUTO_INCREMENT,
  curve_id int,
  as_of_date TIMESTAMP NULL,
  term DOUBLE,
  value DOUBLE,
  creation_date TIMESTAMP NULL,
  PRIMARY KEY (id)
);

-- Table rating
DROP TABLE IF EXISTS rating;
CREATE TABLE rating (
  id int NOT NULL AUTO_INCREMENT,
  moodys_rating VARCHAR(125),
  sand_p_rating VARCHAR(125),
  fitch_rating VARCHAR(125),
  order_number int,
  PRIMARY KEY (id)
);

-- Table rulename
DROP TABLE IF EXISTS rulename;
CREATE TABLE rulename (
  id int NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sql_str VARCHAR(125),
  sql_part VARCHAR(125),
  PRIMARY KEY (id)
);

-- Table users
DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id int NOT NULL AUTO_INCREMENT,
  username VARCHAR(125) NOT NULL,
  password VARCHAR(125) NOT NULL,
  fullname VARCHAR(125) NOT NULL,
  role VARCHAR(125) NOT NULL,
  PRIMARY KEY (id)
);

-- Initial data
INSERT INTO users (fullname, username, password, role) VALUES
("Administrator", "admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "ADMIN"),
("User", "user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "USER");

INSERT INTO bidlist (account, type, bid_quantity, ask_quantity, bid, ask, benchmark, bid_list_date, commentary, security, status, trader, book, creation_name, creation_date, revision_name, revision_date, deal_name, deal_type, source_list_id, side) VALUES
('Account1', 'TypeA', 100.0, 150.0, 50.5, 51.0, 'BenchmarkA', NOW(), 'CommentA', 'SecurityA', 'Active', 'TraderA', 'BookA', 'Admin', NOW(), 'Admin', NOW(), 'DealA', 'Type1', 'SourceA', 'SideA'),
('Account2', 'TypeB', 200.0, 250.0, 60.5, 61.0, 'BenchmarkB', NOW(), 'CommentB', 'SecurityB', 'Inactive', 'TraderB', 'BookB', 'Admin', NOW(), 'Admin', NOW(), 'DealB', 'Type2', 'SourceB', 'SideB');

INSERT INTO trade (account, type, buy_quantity, sell_quantity, buy_price, sell_price, trade_date, security, status, trader, benchmark, book, creation_name, creation_date, revision_name, revision_date, deal_name, deal_type, source_list_id, side) VALUES
('Account1', 'TypeA', 50.0, 40.0, 100.5, 102.0, NOW(), 'SecurityA', 'Completed', 'TraderA', 'BenchmarkA', 'BookA', 'Admin', NOW(), 'Admin', NOW(), 'DealA', 'Type1', 'SourceA', 'SideA'),
('Account2', 'TypeB', 60.0, 55.0, 120.5, 122.0, NOW(), 'SecurityB', 'Pending', 'TraderB', 'BenchmarkB', 'BookB', 'Admin', NOW(), 'Admin', NOW(), 'DealB', 'Type2', 'SourceB', 'SideB');

INSERT INTO curvepoint (curve_id, as_of_date, term, value, creation_date) VALUES
(1, NOW(), 5.0, 105.5, NOW()),
(2, NOW(), 10.0, 110.0, NOW());

INSERT INTO rating (moodys_rating, sand_p_rating, fitch_rating, order_number) VALUES
('A1', 'AA', 'AAA', 1),
('Baa2', 'BBB', 'BB+', 2);

INSERT INTO rulename (name, description, json, template, sql_str, sql_part) VALUES
('Rule1', 'Description1', '{"rule":"test1"}', 'Template1', 'SELECT * FROM trade', 'SQLPart1'),
('Rule2', 'Description2', '{"rule":"test2"}', 'Template2', 'SELECT * FROM bidlist', 'SQLPart2');

-- Verifications
SELECT * FROM users;
SELECT * FROM rulename;
SELECT * FROM rating;
SELECT * FROM curvepoint;
SELECT * FROM trade;
SELECT * FROM bidlist;