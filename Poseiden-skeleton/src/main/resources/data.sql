CREATE DATABASE poseidon_db CHARACTER SET utf8mb4;

USE poseidon_db;

CREATE TABLE bid_list (
  bid_list_id int NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bid_quantity DOUBLE,
  ask_quantity DOUBLE,
  bid DOUBLE,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bid_list_date TIMESTAMP,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),
  PRIMARY KEY (bid_list_id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE trade (
  trade_id int NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buy_quantity DOUBLE,
  sell_quantity DOUBLE,
  buy_price DOUBLE ,
  sell_price DOUBLE,
  trade_date TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),
  PRIMARY KEY (trade_id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE curve_point (
  id int NOT NULL AUTO_INCREMENT,
  curve_id int,
  as_of_date TIMESTAMP,
  term DOUBLE,
  value DOUBLE,
  creation_date TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE rating (
  id int NOT NULL AUTO_INCREMENT,
  moodys_rating VARCHAR(125),
  sandp_rating VARCHAR(125),
  fitch_rating VARCHAR(125),
  order_number int,
  PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE rule_name (
  id int NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sql_str VARCHAR(125),
  sql_part VARCHAR(125),
  PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE users (
  id INTEGER NOT NULL,
  username VARCHAR(125),
  password VARCHAR(125),
  fullname VARCHAR(125),
  role VARCHAR(125),
  PRIMARY KEY (id)
) ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

INSERT INTO users(id, fullname, username, password, role) VALUES
(1, "Administrator", "admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "ADMIN"),
(2, "User", "user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "USER");
