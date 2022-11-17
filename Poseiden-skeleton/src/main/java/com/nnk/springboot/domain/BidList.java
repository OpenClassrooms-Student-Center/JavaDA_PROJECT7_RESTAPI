package com.nnk.springboot.domain;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BidListId")
    private int bidListId;

    @Column(name = "account")
    private String account;

    @Column(name = "type")
    private String type;

    @Column(name = "bidQuantity")
    private double bidQuantity;

    @Column(name = "askQuantity")
    private double askQuantity;
    
    @Column(name = "bid")
    private double bid;

    @Column(name = "ask")
    private double ask;
    
    @Column(name = "benchmark")
    private String benchmark;
    
    @Column(name = "bidListDate")
    private String bidListDate;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;
    
    @Column(name = "revisionName")
    private String revisionName;
    
    @Column(name = "revisionDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date revisionDate;    
    
    @Column(name = "dealName")
    private String dealName;

    @Column(name = "dealType")
    private String dealType;

    @Column(name = "sourceListId")
    private String sourceListId;
    
    @Column(name = "side")
    private String side;
}
