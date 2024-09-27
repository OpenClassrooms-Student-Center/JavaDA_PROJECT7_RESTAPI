
CREATE TABLE `BidList` (
  `BidListId` tinyint(4) NOT NULL AUTO_INCREMENT DEFAULT 0,
  `account` VARCHAR(30) NOT NULL,
  `type` VARCHAR(30) NOT NULL,
  `bidQuantity` DOUBLE,
  `askQuantity` DOUBLE,
  `bid` DOUBLE,
  `ask` DOUBLE,
  `benchmark` VARCHAR(125),
  `bidListDate` TIMESTAMP,
  `commentary` VARCHAR(125),
  `security` VARCHAR(125),
  `status` VARCHAR(10),
  `trader` VARCHAR(125),
  `book` VARCHAR(125),
  `creationName` VARCHAR(125),
  `creationDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `revisionName` VARCHAR(125),
  `revisionDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `dealName` VARCHAR(125),
  `dealType` VARCHAR(125),
  `sourceListId` VARCHAR(125),
  `side` VARCHAR(125),
  PRIMARY KEY (`BidListId`)
)

CREATE TABLE Trade (
  TradeId tinyint(4) NOT NULL AUTO_INCREMENT DEFAULT 0,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buyQuantity DOUBLE,
  sellQuantity DOUBLE,
  buyPrice DOUBLE ,
  sellPrice DOUBLE,
  tradeDate TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  revisionName VARCHAR(125),
  revisionDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (TradeId)
)

CREATE TABLE CurvePoint (
  Id tinyint(4) NOT NULL AUTO_INCREMENT DEFAULT 0,
  CurveId tinyint,
  asOfDate TIMESTAMP,
  term DOUBLE ,
  value DOUBLE ,
  creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

  PRIMARY KEY (Id)
)

CREATE TABLE Rating (
  Id tinyint(4) NOT NULL AUTO_INCREMENT DEFAULT 0,
  moodysRating VARCHAR(125),
  sandPRating VARCHAR(125),
  fitchRating VARCHAR(125),
  orderNumber tinyint,

  PRIMARY KEY (Id)
)

CREATE TABLE RuleName (
  Id tinyint(4) NOT NULL AUTO_INCREMENT DEFAULT 0,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sqlStr VARCHAR(125),
  sqlPart VARCHAR(125),

  PRIMARY KEY (Id)
)

CREATE TABLE Users (
  Id tinyint(4) NOT NULL AUTO_INCREMENT DEFAULT 0,
  username VARCHAR(125),
  password VARCHAR(125),
  fullname VARCHAR(125),
  role VARCHAR(125),

  PRIMARY KEY (Id)
)

insert into Users(id, fullname, username, password, role) values(1 ,"Administrator", "admin", "$2a$10$rWiF5iaOSzHiJt29eHkiM.roj71s2g2vHufSok4402a7Cx9oRnbrm", "ADMIN")
insert into Users(id, fullname, username, password, role) values(2 ,"User", "user", "$2a$10$nou/kIhl82X2OVwYBqHCy.iJdCoDV2rwkIltAjEv.vH3uyWXGCo2y", "USER")