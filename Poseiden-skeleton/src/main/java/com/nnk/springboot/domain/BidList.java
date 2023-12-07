package com.nnk.springboot.domain;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;

import java.util.Date;

@Entity
@Table(name = "bidlist")
public class BidList {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 30, nullable = false)
    @Size(max = 30, message = "account should be less than 31 characters")
    @NotBlank(message = "account is mandatory")
    private String account;

    @Column(length = 30, nullable = false)
    @Size(max = 30, message = "type should be less than 31 characters")
    @NotBlank(message = "type is mandatory")
    private String type;

    @Column
    @PositiveOrZero(message = "bidQuantity should be a positive number or zero")
    private Double bidQuantity;

    @Column
    @PositiveOrZero(message = "askQuantity should be a positive number or zero")
    private Double askQuantity;

    @Column
    @PositiveOrZero(message = "bid should be a positive number or zero")
    private Double bid;

    @Column
    @PositiveOrZero(message = "ask should be a positive number or zero")
    private Double ask;

    @Column(length = 125)
    @Size(max = 125, message = "benchmark should be less than 126 characters")
    private String benchmark;

    @Column
    private Date bidListDate;

    @Column(length = 125)
    @Size(max = 125, message = "commentary should be less than 126 characters")
    private String commentary;

    @Column(length = 125)
    @Size(max = 125, message = "security should be less than 126 characters")
    private String security;

    @Column(length = 10)
    @Size(max = 10, message = "status should be less than 11 characters")
    private String status;

    @Column(length = 125)
    @Size(max = 125, message = "trader should be less than 126 characters")
    private String trader;

    @Column(length = 125)
    @Size(max = 125, message = "book should be less than 126 characters")
    private String book;

    @Column(length = 125)
    @Size(max = 125, message = "creationName should be less than 126 characters")
    private String creationName;

    @Column
    private Date creationDate;

    @Column(length = 125)
    @Size(max = 125, message = "revisionName should be less than 126 characters")
    private String revisionName;

    @Column
    private Date revisionDate;

    @Column(length = 125)
    @Size(max = 125, message = "dealName should be less than 126 characters")
    private String dealName;

    @Column(length = 125)
    @Size(max = 125, message = "dealType should be less than 126 characters")
    private String dealType;

    @Column(length = 125)
    @Size(max = 125, message = "sourceListId should be less than 126 characters")
    private String sourceListId;

    @Column(length = 125)
    @Size(max = 125, message = "side should be less than 126 characters")
    private String side;

    public BidList() {}

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(Double bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    public void setAskQuantity(Double askQuantity) {
        this.askQuantity = askQuantity;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public void setSecurity(String security) {
        this.security = security;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public void setCreationName(String creationName) {
        this.creationName = creationName;
    }

    public void setRevisionName(String revisionName) {
        this.revisionName = revisionName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public void setSourceListId(String sourceListId) {
        this.sourceListId = sourceListId;
    }

    public void setSide(String side) {
        this.side = side;
    }

    @Override
    public String toString() {
        return "&account=" + this.account +
                "&type=" + this.type +
                "&bidQuantity=" + this.bidQuantity;
    }
}
