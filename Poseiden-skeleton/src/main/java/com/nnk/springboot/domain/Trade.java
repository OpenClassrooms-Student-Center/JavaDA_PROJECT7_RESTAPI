package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "trade")
public class Trade {

    // TODO DONE: Map columns in data table TRADE with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    Integer tradeId;

    @Column(name = "account")
    String account;

    @Column(name = "type")
    String type;

    @Column(name = "buy_quantity")
    Double buyQuantity;

    @Column(name = "sell_quantity")
    Double sellQuantity;

    @Column(name = "buy_price")
    Double buyPrice;

    @Column(name = "sell_price")
    Double sellPrice;

    @Column(name = "benchmark")
    String benchmark;

    @Column(name = "trade_date")
    Timestamp tradeDate;

    @Column(name = "security")
    String security;

    @Column(name = "status")
    String status;

    @Column(name = "trader")
    String trader;

    @Column(name = "book")
    String book;

    @Column(name = "creation_name")
    String creationName;

    @Column(name = "creation_date")
    Timestamp creationDate;

    @Column(name = "revision_name")
    String revisionName;

    @Column(name = "revision_date")
    Timestamp revisionDate;

    @Column(name = "deal_name")
    String dealName;

    @Column(name = "deal_type")
    String dealType;

    @Column(name = "source_list_id")
    String sourceListId;

    @Column(name = "side")
    String side;

}
