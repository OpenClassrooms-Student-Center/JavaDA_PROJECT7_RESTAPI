package com.nnk.springboot.model;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;


@Slf4j
@Entity
@Table(name = "trade")
@NoArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TradeId", nullable = false)
    Integer tradeId;
    @Column(name = "account")
    String account;
    @Column(name = "type")
    String type;
    @Column(name = "buyQuantity")
    Double buyQuantity;

    @Column(name = "sellQuantity")
    Double sellQuantity;

    @Column(name = "buyPrice")
    Double buyPrice;

    @Column(name = "sellPrice")
    Double sellPrice;

    @Column(name = "security")
    String security;

    @Column(name = "status")
    String status;

    @Column(name = "trader")
    String trader;

    @Column(name = "benchmark")
    String benchmark;

    @Column(name = "book")
    String book;

    @Column(name = "creationName")
    String creationName;

    @Column(name = "revisionName")
    String revisionName;

    @Column(name = "dealName")
    String dealName;

    @Column(name = "dealType")
    String dealType;

    @Column(name = "sourceListId")
    String sourceListId;

    @Column(name = "side")
    String side;

    @Column(name = "tradeDate")
    private LocalDateTime tradeDate;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "revisionDate")
    private LocalDateTime revisionDate;

    public Trade(Integer tradeId, String account, String type, Double buyQuantity) {
        this.tradeId = tradeId;
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }
}