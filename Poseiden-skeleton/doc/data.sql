CREATE TABLE BidList (
  Id int NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bidQuantity DOUBLE,
  askQuantity DOUBLE,
  bid DOUBLE ,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bidListDate DATETIME(6),
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate DATETIME(6) ,
  revisionName VARCHAR(125),
  revisionDate DATETIME(6) ,
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (Id)
);

CREATE TABLE Trade (
  Id int NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buyQuantity DOUBLE,
  sellQuantity DOUBLE,
  buyPrice DOUBLE ,
  sellPrice DOUBLE,
  tradeDate DATETIME(6),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate DATETIME(6) ,
  revisionName VARCHAR(125),
  revisionDate DATETIME(6) ,
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (Id)
);

CREATE TABLE CurvePoint (
  Id int NOT NULL AUTO_INCREMENT,
  CurveId int,
  asOfDate DATETIME(6),
  term DOUBLE ,
  value DOUBLE ,
  creationDate DATETIME(6) ,

  PRIMARY KEY (Id)
);

CREATE TABLE Rating (
  Id int NOT NULL AUTO_INCREMENT,
  moodysRating VARCHAR(125),
  sandPRating VARCHAR(125),
  fitchRating VARCHAR(125),
  orderNumber tinyint,

  PRIMARY KEY (Id)
);

CREATE TABLE RuleName (
  Id int NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sqlStr VARCHAR(125),
  sqlPart VARCHAR(125),

  PRIMARY KEY (Id)
);

CREATE TABLE Users (
  Id int NOT NULL AUTO_INCREMENT,
  username VARCHAR(125) NOT NULL UNIQUE,
  password VARCHAR(125) NOT NULL,
  fullname VARCHAR(125),
  role VARCHAR(125),

  PRIMARY KEY (Id)
);

insert into Users(fullname, username, password, role) values("Administrator", "admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "ADMIN");
insert into Users(fullname, username, password, role) values("User", "user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "USER");

create table bidlist_SEQ (next_val bigint) engine=InnoDB;
insert into bidlist_SEQ values ( 1 );
create table curvepoint_SEQ (next_val bigint) engine=InnoDB;
insert into curvepoint_SEQ values ( 1 );
create table rating_SEQ (next_val bigint) engine=InnoDB;
insert into rating_SEQ values ( 1 );
create table rulename_SEQ (next_val bigint) engine=InnoDB;
insert into rulename_SEQ values ( 1 );
create table trade_SEQ (next_val bigint) engine=InnoDB;
insert into trade_SEQ values ( 1 );
create table users_SEQ (next_val bigint) engine=InnoDB;
insert into users_SEQ values ( 3 );