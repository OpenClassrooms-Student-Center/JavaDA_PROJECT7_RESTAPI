package com.nnk.springboot.model;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "bid")
@Slf4j
@NoArgsConstructor
public class Bid {

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @Id
    @Column(name = "BidListId", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer bidListId;
    @Column(name = "account")
    String account;
    @Column(name = "type")
    String type;
    @Column(name = "bidquantity")
    Double bidQuantity;

    @Column(name = "askQuantity")
    Double askQuantity;

    @Column(name = "bid")
    Double bid;

    @Column(name = "ask")
    Double ask;

    @Column(name = "benchmark")
    String benchmark;

    @Column(name = "bidListDate")
    LocalDateTime bidListDate;

    @Column(name = "commentary")
    String commentary;

    @Column(name = "security")
    String security;

    @Column(name = "status")
    String status;

    @Column(name = "trader")
    String trader;

    @Column(name = "book")
    String book;

    @Column(name = "creationName")
    String creationName;

    @Column(name = "creationDate")
    LocalDateTime creationDate = date;

    @Column(name = "revisionName")
    String revisionName;

    @Column(name = "revisionDate")
    LocalDateTime revisionDate;

    @Column(name = "dealName")
    String dealName;

    @Column(name = "dealType")
    String dealType;

    @Column(name = "sourceListId")
    String sourceListId;

    @Column(name = "side")
    String side;

    public Bid(int BidListId, String account, String type, Double bidQuantity) {
        this.bidListId=BidListId;
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public Bid(String account, String type, double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
