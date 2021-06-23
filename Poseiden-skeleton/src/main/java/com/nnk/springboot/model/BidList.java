package com.nnk.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BidList")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BidListId;

    private String account;
    private String type;
    private Double bidQuantity;
    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;
    private Timestamp bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;


    public BidList(String account, String type, double bidQuantity) {
            this.account = account;
            this.type = type;
            this.bidQuantity = bidQuantity;
    }

}
