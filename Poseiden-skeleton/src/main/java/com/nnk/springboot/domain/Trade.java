package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "Trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "account is mandatory")
    @Size(max = 30, message = "account should be less than 31 characters")
    private String account;

    @Column(nullable = false)
    @NotBlank(message = "type is mandatory")
    @Size(max = 30, message = "type should be less than 31 characters")
    private String type;

    @Column
    @PositiveOrZero(message = "buyQuantity should be a positive number or zero")
    private Double buyQuantity;

    @Column
    @PositiveOrZero(message = "sellQuantity should be a positive number or zero")
    private Double sellQuantity;

    @Column
    @PositiveOrZero(message = "buyPrice should be a positive number or zero")
    private Double buyPrice;

    @Column
    @PositiveOrZero(message = "sellPrice should be a positive number or zero")
    private Double sellPrice;

    @Column
    private Date tradeDate;

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
    @Size(max = 125, message = "benchmark should be less than 126 characters")
    private String benchmark;

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

    public Trade() {}

    public Trade(String account, String type, Double buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
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

    public Double getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(Double buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public void setSellQuantity(Double sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
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

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
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
                "&buyQuantity=" + this.buyQuantity;
    }
}
