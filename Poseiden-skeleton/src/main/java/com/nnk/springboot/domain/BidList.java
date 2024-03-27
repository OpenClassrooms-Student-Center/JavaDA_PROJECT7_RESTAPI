package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
public class BidList {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "bidListId")
    private int bidListId;
    @NotNull(message = "Must not be null")
    @Column(name = "account")
    private String account;
    @NotNull(message = "Must not be null")
    @Column(name = "type")
    private String type;
    @NotNull(message = "Must not be null") //Maybe not ?
    @Column(name = "bidQuantity")
    private Double bidQuantity;
    @Column(name = "askQuantity")
    private Double askQuantity;
    @Column(name = "bid")
    private Double bid;
    @Column(name = "ask")
    private Double ask;
    @Column(name = "benchmark")
    private String benchmark;
    @Column(name = "bidListDate")
    private Timestamp bidListDate;
    @Column(name = "commentary")
    private String commentary;
    @Column(name = "security")
    private String security;
    @Column(name = "status")
    private String status;
    @Column(name = "trader")
    private String trader;
    @Column(name = "book")
    private String book;
    @Column(name = "creationName")
    private String creationName;
    @Column(name = "creationDate")
    private Timestamp creationDate;
    @Column(name = "revisionName")
    private String revisionName;
    @Column(name = "revisionDate")
    private Timestamp revisionDate;
    @Column(name = "dealName")
    private String dealName;
    @Column(name = "dealType")
    private String dealType;
    @Column(name = "sourceListId")
    private String sourceListId;
    @Column(name = "side")
    private String side;

    public BidList() {}

    public int getBidListId() {
        return bidListId;
    }
    public void setBidListId(int bidListId) {
        this.bidListId = bidListId;
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
    public Double getAskQuantity() {
        return askQuantity;
    }
    public void setAskQuantity(Double askQuantity) {
        this.askQuantity = askQuantity;
    }
    public Double getBid() {
        return bid;
    }
    public void setBid(Double bid) {
        this.bid = bid;
    }
    public Double getAsk() {
        return ask;
    }
    public void setAsk(Double ask) {
        this.ask = ask;
    }
    public String getBenchmark() {
        return benchmark;
    }
    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }
    public Timestamp getBidListDate() {
        return bidListDate;
    }
    public void setBidListDate(Timestamp bidListDate) {
        this.bidListDate = bidListDate;
    }
    public String getCommentary() {
        return commentary;
    }
    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }
    public String getSecurity() {
        return security;
    }
    public void setSecurity(String security) {
        this.security = security;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getTrader() {
        return trader;
    }
    public void setTrader(String trader) {
        this.trader = trader;
    }
    public String getBook() {
        return book;
    }
    public void setBook(String book) {
        this.book = book;
    }
    public String getCreationName() {
        return creationName;
    }
    public void setCreationName(String creationName) {
        this.creationName = creationName;
    }
    public Timestamp getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
    public String getRevisionName() {
        return revisionName;
    }
    public void setRevisionName(String revisionName) {
        this.revisionName = revisionName;
    }
    public Timestamp getRevisionDate() {
        return revisionDate;
    }
    public void setRevisionDate(Timestamp revisionDate) {
        this.revisionDate = revisionDate;
    }
    public String getDealName() {
        return dealName;
    }
    public void setDealName(String dealName) {
        this.dealName = dealName;
    }
    public String getDealType() {
        return dealType;
    }
    public void setDealType(String dealType) {
        this.dealType = dealType;
    }
    public String getSourceListId() {
        return sourceListId;
    }
    public void setSourceListId(String sourceListId) {
        this.sourceListId = sourceListId;
    }
    public String getSide() {
        return side;
    }
    public void setSide(String side) {
        this.side = side;
    }
}
