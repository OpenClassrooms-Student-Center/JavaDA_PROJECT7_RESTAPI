DROP TABLE IF EXISTS BidList;
DROP TABLE IF EXISTS Trade;
DROP TABLE IF EXISTS CurvePoint;
DROP TABLE IF EXISTS Rating;
DROP TABLE IF EXISTS Rule;
DROP TABLE IF EXISTS Users;

CREATE TABLE BidList (
                         id tinyint(4) NOT NULL AUTO_INCREMENT,
                         account VARCHAR(30) NOT NULL,
                         type VARCHAR(30) NOT NULL,
                         bidQuantity DOUBLE,
                         askQuantity DOUBLE,
                         bid DOUBLE ,
                         ask DOUBLE,
                         benchmark VARCHAR(125),
                         bidListDate TIMESTAMP,
                         commentary VARCHAR(125),
                         security VARCHAR(125),
                         status VARCHAR(10),
                         trader VARCHAR(125),
                         book VARCHAR(125),
                         creationName VARCHAR(125),
                         creationDate TIMESTAMP ,
                         revisionName VARCHAR(125),
                         revisionDate TIMESTAMP ,
                         dealName VARCHAR(125),
                         dealType VARCHAR(125),
                         sourceListId VARCHAR(125),
                         side VARCHAR(125),

                         PRIMARY KEY (id)
);
CREATE TABLE Trade (
                       TradeId tinyint(4) NOT NULL AUTO_INCREMENT,
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
                       creationDate TIMESTAMP ,
                       revisionName VARCHAR(125),
                       revisionDate TIMESTAMP ,
                       dealName VARCHAR(125),
                       dealType VARCHAR(125),
                       sourceListId VARCHAR(125),
                       side VARCHAR(125),

                       PRIMARY KEY (TradeId)
);
CREATE TABLE CurvePoint (
                            Id tinyint(4) NOT NULL AUTO_INCREMENT,
                            CurveId tinyint,
                            asOfDate TIMESTAMP,
                            term DOUBLE ,
                            value DOUBLE ,
                            creationDate TIMESTAMP ,

                            PRIMARY KEY (Id)
);
CREATE TABLE Rating (
                        Id tinyint(4) NOT NULL AUTO_INCREMENT,
                        moodys VARCHAR(125),
                        sandP VARCHAR(125),
                        fitch VARCHAR(125),
                        orderName tinyint,

                        PRIMARY KEY (Id)
);
CREATE TABLE Rule (
                          Id tinyint(4) NOT NULL AUTO_INCREMENT,
                          name VARCHAR(125),
                          description VARCHAR(125),
                          json VARCHAR(125),
                          template VARCHAR(512),
                          sqlStr VARCHAR(125),
                          sqlPart VARCHAR(125),

                          PRIMARY KEY (Id)
);
CREATE TABLE Users (
                       Id tinyint(4) NOT NULL AUTO_INCREMENT,
                       username VARCHAR(125),
                       password VARCHAR(125),
                       fullname VARCHAR(125),
                       role VARCHAR(125),

                       PRIMARY KEY (Id)
);
insert into Users(id,fullname, username, password, role) values(1,'Administrator', 'adminname', '$2a$10$WcKH1lUm0pth4xOdAvLlI.zHBT2vTegsxL.829nIir4RmkiTIYvT6', 'ROLE_ADMIN');
insert into Users(id,fullname, username, password, role) values(2,'User', 'username', '$2a$10$WcKH1lUm0pth4xOdAvLlI.zHBT2vTegsxL.829nIir4RmkiTIYvT6', 'ROLE_USER');
